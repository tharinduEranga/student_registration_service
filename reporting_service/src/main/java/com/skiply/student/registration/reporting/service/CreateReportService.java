package com.skiply.student.registration.reporting.service;

import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.reporting.mapper.ReportRepositoryModelMapper;
import com.skiply.student.registration.reporting.model.Report;
import com.skiply.student.registration.reporting.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateReportService.class);

    private final ReportRepository reportRepository;

    public CreateReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public String execute(Report report) {
        try {
            var reportRecord = ReportRepositoryModelMapper.getPaymentDataRecordFromPayment(report);
            reportRecord = reportRepository.save(reportRecord);
            return ReportRepositoryModelMapper.getPaymentFromPaymentDataRecord(reportRecord)
                    .id().value();
        } catch (Exception e) {
            LOGGER.error("Failed to save report: {}", e.getMessage(), e);
            throw new TransientFailure("Failed to save report");
        }
    }
}
