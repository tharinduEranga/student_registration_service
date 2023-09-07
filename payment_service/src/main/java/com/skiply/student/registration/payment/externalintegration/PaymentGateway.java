package com.skiply.student.registration.payment.externalintegration;

import com.skiply.student.registration.payment.externalintegration.model.PaymentInitiationGatewayRequest;
import com.skiply.student.registration.payment.externalintegration.model.PaymentInitiationGatewayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentGateway {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentGateway.class);


    public PaymentInitiationGatewayResponse pay(PaymentInitiationGatewayRequest paymentInitiationGatewayRequest) {
        LOGGER.info("Calling payment gateway: {}", paymentInitiationGatewayRequest);
        //call the actual payment gateway here.
        //we don't have a real payment gateway integration, so will return a static response
        return PaymentInitiationGatewayResponse.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }
}
