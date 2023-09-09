package com.skiply.student.registration.reporting.api.controller;

import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.payment.ReportsApi;
import com.skiply.student.registration.payment.model.GetPaymentReportByPaymentIdResponse;
import com.skiply.student.registration.reporting.mapper.ReportApiModelMapper;
import com.skiply.student.registration.reporting.service.GetReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportsController implements ReportsApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportsController.class);

    private final GetReportService getReportService;

    public ReportsController(GetReportService getReportService) {
        this.getReportService = getReportService;
    }

    @Override
    public ResponseEntity<GetPaymentReportByPaymentIdResponse> getPaymentReport(String paymentId) {
        LOGGER.info("Get report request: {}", paymentId);
        final var report = getReportService.execute(PaymentId.of(paymentId));
        final var reportResponse = ReportApiModelMapper.getGetPaymentReportByPaymentIdResponseFromReport(report);
        LOGGER.info("Get report response: {}", reportResponse);
        return ResponseEntity.ok(reportResponse);
    }
}
