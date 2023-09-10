package com.skiply.student.registration.reporting.service;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.reporting.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateStudentDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStudentDataService.class);

    private final ReportRepository reportRepository;

    public UpdateStudentDataService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public void execute(PaymentId paymentId, Student student) {
        try {
            final var reportRecord = reportRepository.findByPaymentId(paymentId.value())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new BusinessRuleViolationException("No report found for the student: %s"
                            .formatted(student.id().value())));
            reportRecord.setStudentName(student.name());
            reportRepository.save(reportRecord);
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("Failed to update student in report: {}", e.getMessage(), e);
            throw new TransientFailure("Failed to update student in report");
        }
    }
}
