package com.skiply.student.registration.payment.mapper;

import com.skiply.student.registration.common.model.Payment;
import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.payment.model.KeyValuePair;
import com.skiply.student.registration.payment.model.PaymentConfirmation;
import com.skiply.student.registration.payment.model.PaymentConfirmationRequest;
import com.skiply.student.registration.payment.model.PaymentInitiationRequest;
import org.javamoney.moneta.FastMoney;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentApiModelMapper {

    private PaymentApiModelMapper() {
    }

    public static Payment getPaymentFromInitiationRequest(PaymentInitiationRequest request) {
        return Payment.builder()
                .id(PaymentId.random())
                .amount(FastMoney.of(request.getAmount(), request.getCurrency()))
                .studentRegistrationId(request.getStudentRegistrationId())
                .status(PaymentStatus.PENDING)
                .description(request.getDescription())
                .metadata(mapMetaData(request.getMetadata()))
                .initiatedAt(OffsetDateTime.now())
                .build();
    }

    public static PaymentConfirmation getPaymentConfirmationFromRequest(PaymentConfirmationRequest request) {
        return PaymentConfirmation.builder()
                .id(PaymentId.of(request.getReference()))
                .status(PaymentStatus.valueOf(request.getStatus().getValue()))
                .confirmedAt(OffsetDateTime.now())
                .build();
    }

    private static Map<String, String> mapMetaData(List<KeyValuePair> keyValuePairs) {
        return keyValuePairs
                .stream()
                .collect(Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue,
                        (a, b) -> b, HashMap::new));
    }
}
