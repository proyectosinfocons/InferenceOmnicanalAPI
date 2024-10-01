package com.inference.whatsappintegration.controller;

import com.inference.whatsappintegration.application.dto.whatsapp.whatsappdeliverystatus.WhatsappDeliveryStatus;
import com.inference.whatsappintegration.application.dto.whatsapp.whatsappoldinrequest.WhatsappOldIncomingMessageRequestDTO;
import com.inference.whatsappintegration.domain.service.WhatsappService;
import com.inference.whatsappintegration.infrastructure.config.mdc.MdcAwareExecutor;
import com.inference.whatsappintegration.util.Constants;
import com.inference.whatsappintegration.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhatsappController.class);

    private WhatsappService whatsappService;
    private MdcAwareExecutor mdcAwareExecutor;

    public WhatsappController(WhatsappService processWhatsappInteraction, MdcAwareExecutor mdcAwareExecutor){
        this.whatsappService = processWhatsappInteraction;
        this.mdcAwareExecutor = mdcAwareExecutor;
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> getCallback(){
        LOGGER.info("Get callback call");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/callback")
    public ResponseEntity<String> eventInput(@RequestBody WhatsappOldIncomingMessageRequestDTO whatsappOldIncomingMessageRequestDTO){
        LOGGER.info("Receive whatsapp process request");
        //Validators.validateWhatsappDto(whatsappIncomingMessageRequestDTO);
        mdcAwareExecutor.execute(() -> whatsappService.processReceiveInteractionOld(whatsappOldIncomingMessageRequestDTO));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<Void> getStatus(){
        LOGGER.info("Get status call");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/status")
    public ResponseEntity<String> eventStatus(@RequestBody WhatsappDeliveryStatus whatsappDeliveryStatus){
        if (whatsappDeliveryStatus.getStatus().equals(Constants.DEFAULT_WSP_DELIVERY_STATUS_SENT)){
            LOGGER.info("Receive whatsapp sent status");
            Utils.logAsJson(LOGGER, whatsappDeliveryStatus, "WhatsappDeliveryStatusRequest");
        }
        return ResponseEntity.ok().build();
    }

}
