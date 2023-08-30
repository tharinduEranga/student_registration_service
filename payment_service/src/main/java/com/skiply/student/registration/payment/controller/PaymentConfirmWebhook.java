package com.skiply.student.registration.payment.controller;

import com.skiply.student.registration.payment.PaymentsConfirmApi;
import com.skiply.student.registration.payment.model.PaymentConfirmationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentConfirmWebhook implements PaymentsConfirmApi {

    @Override
    public ResponseEntity<Void> confirmPayment(PaymentConfirmationRequest paymentConfirmationRequest) {
        return null;
    }
}
