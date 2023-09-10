package com.skiply.student.registration.reporting.service;

import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.reporting.mapper.ReportRepositoryModelMapper;
import com.skiply.student.registration.reporting.model.Report;
import com.skiply.student.registration.reporting.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateReportService.class);

    private final ReportRepository reportRepository;

    public UpdateReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public String execute(Report report) {
        try {
            var reportRecord = ReportRepositoryModelMapper.getReportDataRecordFromReport(report);

            //get existing report
            var reportByStudent = reportRepository
                    .findByStudentRegistrationId(report.studentRegistrationId().value())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new BusinessRuleViolationException("No report found for the student: %s"
                            .formatted(report.studentRegistrationId().value())));

            //set existing values
            reportRecord.setId(reportByStudent.getId());
            reportRecord.setStudentName(reportByStudent.getStudentName());
            reportRecord = reportRepository.save(reportRecord);
            return ReportRepositoryModelMapper.getReportFromReportDataRecord(reportRecord)
                    .id().value();

        } catch (Exception e) {
            LOGGER.error("Failed to save report: {}", e.getMessage(), e);
            throw new TransientFailure("Failed to save report");
        }
    }
}
