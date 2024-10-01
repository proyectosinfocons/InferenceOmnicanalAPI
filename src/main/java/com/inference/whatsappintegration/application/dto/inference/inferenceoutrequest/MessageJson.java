package com.inference.whatsappintegration.application.dto.inference.inferenceoutrequest;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class MessageJson {

    private String userId;

    private String idRecipient;

    private String channel;

    private String clientName;

    private String agent;
}
