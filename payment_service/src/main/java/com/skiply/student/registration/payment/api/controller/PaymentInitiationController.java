package com.skiply.student.registration.payment.api.controller;

import com.skiply.student.registration.payment.PaymentsInitApi;
import com.skiply.student.registration.payment.mapper.PaymentApiModelMapper;
import com.skiply.student.registration.payment.model.PaymentInitiationRequest;
import com.skiply.student.registration.payment.model.PaymentInitiationResponse;
import com.skiply.student.registration.payment.service.PaymentInitiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentInitiationController implements PaymentsInitApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInitiationController.class);

    private final PaymentInitiator paymentInitiator;

    public PaymentInitiationController(PaymentInitiator paymentInitiator) {
        this.paymentInitiator = paymentInitiator;
    }

    @Override
    public ResponseEntity<PaymentInitiationResponse> initPayment(PaymentInitiationRequest paymentInitiationRequest) {
        LOGGER.info("Initiate payment request: {}", paymentInitiationRequest);
        var payment = PaymentApiModelMapper.getPaymentFromInitiationRequest(paymentInitiationRequest);
        var paymentId = paymentInitiator.execute(payment);
        LOGGER.info("Initiate payment response: {}", paymentId);
        return ResponseEntity.ok(new PaymentInitiationResponse().id(paymentId));
    }
}
