package com.skiply.student.registration.reporting.service;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.reporting.repository.ReportRepository;
import com.skiply.student.registration.reporting.repository.data.ReportRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class CreateInitialReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateInitialReportService.class);

    private final ReportRepository reportRepository;

    public CreateInitialReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public void execute(Student student) {
        try {
            final var reportRecord = ReportRecord.builder()
                    .id(UUID.randomUUID().toString())
                    .studentRegistrationId(student.id().value())
                    .studentName(student.name())
                    .datetime(OffsetDateTime.now())
                    .build();
            reportRepository.save(reportRecord);
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("Failed to update student in report: {}", e.getMessage(), e);
            throw new TransientFailure("Failed to update student in report");
        }
    }
}
