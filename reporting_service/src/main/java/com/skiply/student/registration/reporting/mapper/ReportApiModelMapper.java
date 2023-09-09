package com.skiply.student.registration.reporting.mapper;

import com.skiply.student.registration.common.model.id.ReportId;
import com.skiply.student.registration.common.model.kafka.PaymentConfirmationReportEvent;
import com.skiply.student.registration.payment.model.GetPaymentReportByPaymentIdResponse;
import com.skiply.student.registration.reporting.model.Report;
import org.javamoney.moneta.FastMoney;

import java.math.BigDecimal;

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

    public static GetPaymentReportByPaymentIdResponse getGetPaymentReportByPaymentIdResponseFromReport(Report report) {
        return new GetPaymentReportByPaymentIdResponse()
                .studentId(report.studentRegistrationId().value())
                .studentName(report.studentName())
                .maskedCardNo(report.cardDetails().maskedCardNo())
                .cardType(report.cardDetails().cardType().toString())
                .transactionAmount(report.amount().getNumber().numberValue(BigDecimal.class))
                .transactionDateTime(report.datetime().toString())
                .transactionReference(report.paymentId().value());
    }
}
