package com.skiply.student.registration.payment.api.controller;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PaymentInitiationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("GIVEN valid payment data WHEN call initiate payment api THEN payment init success response is returned")
    void initiatePaymentSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/payments-init")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "amount": 100.00,
                                    "currency": "USD",
                                    "reference": "123456",
                                    "studentRegistrationId": "student123",
                                    "description": "Payment for Course Enrollment",
                                    "metadata": [
                                        {
                                            "key": "transactionType",
                                            "value": "enrollment"
                                        },
                                        {
                                            "key": "user",
                                            "value": "john.doe"
                                        }
                                    ]
                                }
                                """)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty()); //because the id is dynamic
    }

    @Test
    @DisplayName("GIVEN invalid payment data without amount WHEN call initiate payment api THEN payment init error message is returned")
    void initiatePaymentInvalidRequestErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/payments-init")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "amount": null,
                                    "currency": "USD",
                                    "reference": "123456",
                                    "studentRegistrationId": "student123",
                                    "description": "Payment for Course Enrollment",
                                    "metadata": [
                                        {
                                            "key": "transactionType",
                                            "value": "enrollment"
                                        },
                                        {
                                            "key": "user",
                                            "value": "john.doe"
                                        }
                                    ]
                                }
                                """)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.Errors[0].Message").value("amount: must not be null"));
    }
}
