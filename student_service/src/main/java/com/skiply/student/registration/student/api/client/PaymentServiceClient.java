package com.skiply.student.registration.student.api.client;

import com.skiply.student.registration.student.api.client.model.PaymentInitiateRequest;
import com.skiply.student.registration.student.api.client.model.PaymentInitiateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentServiceClient", url = "http://localhost:8082", path = "")
public interface PaymentServiceClient {

    @PostMapping("/payments-init")
    ResponseEntity<PaymentInitiateResponse> initiatePayment(@RequestBody PaymentInitiateRequest paymentInitiateRequest);
}
