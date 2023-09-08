package com.skiply.student.registration.reporting.mapper;

import com.skiply.student.registration.common.model.id.ReportId;
import com.skiply.student.registration.common.model.kafka.PaymentConfirmationReportEvent;
import com.skiply.student.registration.reporting.model.Report;
import org.javamoney.moneta.FastMoney;

public class ReportApiModelMapper {
    private ReportApiModelMapper() {
    }

    public static Report getPaymentFromPaymentConfirmationEvent(PaymentConfirmationReportEvent event) {
        return Report.builder()
                .id(ReportId.random())
                .amount(FastMoney.of(event.amount(), event.currency()))
                .studentRegistrationId(event.studentRegistrationId())
                .paymentId(event.paymentId())
                .datetime(event.datetime())
                .cardDetails(event.cardDetails())
                .build();
    }
}
