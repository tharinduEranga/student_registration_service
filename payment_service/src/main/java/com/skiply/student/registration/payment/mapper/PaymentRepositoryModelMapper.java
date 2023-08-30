package com.skiply.student.registration.payment.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiply.student.registration.common.model.Payment;
import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.payment.repository.data.PaymentDataRecord;
import org.javamoney.moneta.FastMoney;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class PaymentRepositoryModelMapper {
    private PaymentRepositoryModelMapper() {
    }

    public static PaymentDataRecord getPaymentDataRecordFromPayment(Payment payment) {
        return PaymentDataRecord.builder()
                .id(payment.id().value())
                .amount(payment.amount().getNumber().numberValue(BigDecimal.class))
                .currency(payment.amount().getCurrency().getCurrencyCode())
                .studentRegistrationId(payment.studentRegistrationId())
                .status(payment.status().toString())
                .description(payment.description())
                .metadata(getMetaDataString(payment.metadata()))
                .initiatedAt(OffsetDateTime.now())
                .build();
    }

    public static Payment getPaymentFromPaymentDataRecord(PaymentDataRecord record) {
        return Payment.builder()
                .id(new PaymentId(record.getId()))
                .amount(FastMoney.of(record.getAmount(), record.getCurrency()))
                .studentRegistrationId(record.getStudentRegistrationId())
                .paymentIdFromGateway(record.getPaymentIdFromGateway())
                .status(PaymentStatus.valueOf(record.getStatus()))
                .description(record.getDescription())
                .metadata(getMetaData(record.getMetadata()))
                .initiatedAt(record.getInitiatedAt())
                .confirmedAt(record.getConfirmedAt())
                .build();
    }


    private static String getMetaDataString(Map<String, String> metaData) {
        try {
            return new ObjectMapper().writeValueAsString(metaData);
        } catch (JsonProcessingException e) {
            throw new TransientFailure("Failed to write metadata!", e);
        }
    }

    private static Map<String, String> getMetaData(String metaDataString) {
        try {
            return new ObjectMapper().readValue(metaDataString, new TypeReference<HashMap<String, String>>() {});
        } catch (JsonProcessingException e) {
            throw new TransientFailure("Failed to read metadata!", e);
        }
    }
}
