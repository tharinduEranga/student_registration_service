package com.skiply.student.registration.payment.model;


import javax.money.MonetaryAmount;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Objects;

/**
 * This will be the request used in the payment gateway's payment initiation
 */
public record PaymentInitiationRequest(
        MonetaryAmount amount,
        String reference, //(Payment id from the system and this will be returned to the webhook)
        String description,
        HashMap<String, Object> metaData,
        OffsetDateTime dateTime //(Of the time of initiation in this system)
) {
    public PaymentInitiationRequest {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(reference);
        Objects.requireNonNull(dateTime);
    }
}
