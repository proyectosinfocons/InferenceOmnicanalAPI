package com.inference.whatsappintegration.application.dto.whatsapp.whatappoutrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Message {

    private String requestId;
    private String cascadeId;
    private SubscriberFilter subscriberFilter;
    private String startTime;

}
