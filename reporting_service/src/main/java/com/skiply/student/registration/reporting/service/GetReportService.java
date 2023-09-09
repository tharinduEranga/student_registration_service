package com.skiply.student.registration.reporting.service;

import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.reporting.mapper.ReportRepositoryModelMapper;
import com.skiply.student.registration.reporting.model.Report;
import com.skiply.student.registration.reporting.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetReportService.class);

    private final ReportRepository reportRepository;

    public GetReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    public Report execute(final PaymentId paymentId) {
        try {
            final var reportRecord = reportRepository.findByPaymentId(paymentId.value())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new BusinessRuleViolationException("No report found for the payment: %s"
                            .formatted(paymentId.value())));
            return ReportRepositoryModelMapper.getReportFromReportDataRecord(reportRecord);
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("Failed to get report: {}", e.getMessage(), e);
            throw new TransientFailure("Failed to get report");
        }
    }
}
