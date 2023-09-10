package com.skiply.student.registration.student.service;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.exception.TransientFailure;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.common.model.kafka.StudentCreatedReportEvent;
import com.skiply.student.registration.student.api.client.PaymentServiceClient;
import com.skiply.student.registration.student.api.client.model.PaymentInitiateRequest;
import com.skiply.student.registration.student.event.publisher.StudentCreateEventPublisher;
import com.skiply.student.registration.student.mapper.StudentRepositoryModelMapper;
import com.skiply.student.registration.student.model.StudentCreatedData;
import com.skiply.student.registration.student.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class StudentCreator {

    private final StudentRepository studentRepository;
    private final PaymentServiceClient paymentServiceClient;
    private final StudentCreateEventPublisher studentCreateEventPublisher;

    public StudentCreator(StudentRepository studentRepository, PaymentServiceClient paymentServiceClient, StudentCreateEventPublisher studentCreateEventPublisher) {
        this.studentRepository = studentRepository;
        this.paymentServiceClient = paymentServiceClient;
        this.studentCreateEventPublisher = studentCreateEventPublisher;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCreator.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public StudentCreatedData execute(Student student) {
        try {
            checkMobileNumber(student.mobile());
            var studentRecord = StudentRepositoryModelMapper.getStudentDataRecord(student);
            studentRecord = studentRepository.save(studentRecord);
            final var paymentId = initiatePayment(student);
            publishStudentCreatedEvent(student);
            student = StudentRepositoryModelMapper.getStudentFromDataRecord(studentRecord);
            return new StudentCreatedData(student, paymentId);
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[StudentCreator] failed to save student: {}", e.getMessage(), e);
            throw new BusinessRuleViolationException("Failed to save student: %s".formatted(e.getMessage()), e);
        }
    }


    private void checkMobileNumber(String mobile) {
        if (!studentRepository.findByMobile(mobile).isEmpty()) {
            throw new BusinessRuleViolationException("Mobile number is already registered!");
        }
    }

    private PaymentInitiateRequest getPaymentInitiateRequest(Student student) {
        return PaymentInitiateRequest.builder()
                .studentRegistrationId(student.id().value())
                .amount(BigDecimal.TEN) //Real amount needs to be fetched from the DB
                .currency("AED")
                .description("Student registration")
                .reference(student.id().value()) //it is unique so safe to set
                .build();
    }

    private PaymentId initiatePayment(Student student) {
        var paymentResponse = paymentServiceClient
                .initiatePayment(getPaymentInitiateRequest(student));
        if (!paymentResponse.getStatusCode().is2xxSuccessful()) {
            throw new TransientFailure("Error initiating the payment for student: " + student.id().value());
        }
        LOGGER.info("Payment initiation successful for student: {}", student.id().value());
        if (Objects.isNull(paymentResponse.getBody()) || Objects.isNull(paymentResponse.getBody().id())) {
            throw new TransientFailure("No payment id in the response!");
        }
        return PaymentId.of(paymentResponse.getBody().id());
    }

    private void publishStudentCreatedEvent(Student student) {
        studentCreateEventPublisher.publish(
                StudentCreatedReportEvent.builder()
                        .student(student)
                        .build()
        );
    }

}
