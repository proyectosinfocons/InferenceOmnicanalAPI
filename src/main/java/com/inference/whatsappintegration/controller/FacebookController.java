package com.inference.whatsappintegration.controller;

import com.inference.whatsappintegration.application.dto.facebook.facebookinrequest.FacebookInRequest;
import com.inference.whatsappintegration.domain.service.FacebookService;
import com.inference.whatsappintegration.infrastructure.config.mdc.MdcAwareExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facebook")
public class FacebookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookController.class);

    @Value("${property.facebook.hub.verifytoken}")
    private String verificationToken;

    private MdcAwareExecutor mdcAwareExecutor;

    private FacebookService facebookService;

    public FacebookController(FacebookService facebookService, MdcAwareExecutor mdcAwareExecutor){
        this.mdcAwareExecutor = mdcAwareExecutor;
        this.facebookService = facebookService;
    }

    @GetMapping("/callback")
    public String getFacebookVerification(@RequestParam("hub.mode") String mode,
                                                        @RequestParam("hub.challenge") String challenge,
                                                        @RequestParam("hub.verify_token") String verifyToken){
        LOGGER.info("Get facebook verification call");
        if ("subscribe".equals(mode) && verificationToken.equals(verifyToken)) {
            return challenge;
        }
        LOGGER.info("Fail while verifying the facebook request");
        return "Error in verification";
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> processFacebookEvent(@RequestBody FacebookInRequest facebookInRequest){
        mdcAwareExecutor.execute(() -> facebookService.processReceiveInteraction(facebookInRequest));
        return ResponseEntity.ok().build();
    }

}
