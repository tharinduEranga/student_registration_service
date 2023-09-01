package com.skiply.student.registration.payment.api.controller;

import com.skiply.student.registration.payment.PaymentsConfirmApi;
import com.skiply.student.registration.payment.mapper.PaymentApiModelMapper;
import com.skiply.student.registration.payment.model.PaymentConfirmationRequest;
import com.skiply.student.registration.payment.service.PaymentConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentConfirmWebhook implements PaymentsConfirmApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmWebhook.class);

    private final PaymentConfirmationService paymentConfirmationService;

    public PaymentConfirmWebhook(PaymentConfirmationService paymentConfirmationService) {
        this.paymentConfirmationService = paymentConfirmationService;
    }


    @Override
    public ResponseEntity<Void> confirmPayment(PaymentConfirmationRequest paymentConfirmationRequest) {
        LOGGER.info("Confirm payment request: {}", paymentConfirmationRequest);
        var paymentConfirmation = PaymentApiModelMapper
                .getPaymentConfirmationFromRequest(paymentConfirmationRequest);
        var paymentId = paymentConfirmationService.execute(paymentConfirmation);
        LOGGER.info("Confirm payment response: {}", paymentId);
        return ResponseEntity.ok().build();
    }
}
