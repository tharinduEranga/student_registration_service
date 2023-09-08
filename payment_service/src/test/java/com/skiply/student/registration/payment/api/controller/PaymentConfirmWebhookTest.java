package com.skiply.student.registration.payment.api.controller;

import com.skiply.student.registration.common.model.Payment;
import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.payment.service.PaymentInitiator;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PaymentConfirmWebhookTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentInitiator paymentInitiator;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("GIVEN valid confirm payment data WHEN call confirm payment api THEN payment confirm success response is returned")
    void confirmPaymentSuccessTest() throws Exception {
        var paymentId = paymentInitiator.execute(Payment.builder()
                .id(PaymentId.random())
                .amount(FastMoney.of(100, "AED"))
                .initiatedAt(OffsetDateTime.now())
                .studentRegistrationId(UUID.randomUUID().toString())
                .status(PaymentStatus.PENDING)
                .build()
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/payments-confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "21212",
                                    "reference": "%s",
                                    "status": "SUCCEEDED",
                                    "maskedCardNo": "3244*********8989",
                                    "cardType": "VISA"
                                }
                                """.formatted(paymentId))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("GIVEN invalid id for confirm payment data WHEN call confirm payment api THEN payment confirm error response is returned")
    void confirmPaymentInvalidRequestErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/payments-confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "21212",
                                    "reference": "27f2db09-e374-45b6-8a24-2a2a891532b5",
                                    "status": "SUCCEEDED",
                                    "maskedCardNo": "3244*********8989",
                                    "cardType": "VISA"
                                }
                                """)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.Errors[0].Message").value("No payment found for id: 27f2db09-e374-45b6-8a24-2a2a891532b5"));
    }
}
