package com.skiply.student.registration.reporting.mapper;

import com.skiply.student.registration.common.model.CardDetails;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.reporting.model.Report;
import com.skiply.student.registration.common.model.id.ReportId;
import com.skiply.student.registration.reporting.repository.data.ReportRecord;
import org.javamoney.moneta.FastMoney;

import java.math.BigDecimal;

public class ReportRepositoryModelMapper {
    private ReportRepositoryModelMapper() {
    }

    public static ReportRecord getPaymentDataRecordFromPayment(Report report) {
        return ReportRecord.builder()
                .id(report.id().value())
                .amount(report.amount().getNumber().numberValue(BigDecimal.class))
                .currency(report.amount().getCurrency().getCurrencyCode())
                .studentRegistrationId(report.studentRegistrationId().value())
                .studentName(report.studentName())
                .paymentId(report.paymentId().value())
                .datetime(report.datetime())
                .maskedCardNo(report.cardDetails().maskedCardNo())
                .cardType(report.cardDetails().cardType().toString())
                .build();
    }

    public static Report getPaymentFromPaymentDataRecord(ReportRecord record) {
        return Report.builder()
                .id(ReportId.of(record.getId()))
                .amount(FastMoney.of(record.getAmount(), record.getCurrency()))
                .studentRegistrationId(StudentId.of(record.getStudentRegistrationId()))
                .paymentId(PaymentId.of(record.getPaymentId()))
                .datetime(record.getDatetime())
                .studentName(record.getStudentName())
                .cardDetails(CardDetails.of(record.getMaskedCardNo(), record.getCardType()))
                .build();
    }
}
