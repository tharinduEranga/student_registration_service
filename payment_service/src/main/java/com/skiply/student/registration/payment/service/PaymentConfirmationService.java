package com.skiply.student.registration.payment.service;

import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.common.model.kafka.PaymentConfirmationEvent;
import com.skiply.student.registration.common.model.kafka.PaymentConfirmationReportEvent;
import com.skiply.student.registration.payment.event.PaymentConfirmationEventPublisher;
import com.skiply.student.registration.payment.event.PaymentConfirmationReportEventPublisher;
import com.skiply.student.registration.payment.model.PaymentConfirmation;
import com.skiply.student.registration.payment.repository.PaymentRepository;
import com.skiply.student.registration.payment.repository.data.PaymentDataRecord;
import org.javamoney.moneta.FastMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentConfirmationEventPublisher paymentConfirmationEventPublisher;
    private final PaymentConfirmationReportEventPublisher paymentConfirmationReportEventPublisher;

    public PaymentConfirmationService(PaymentRepository paymentRepository, PaymentConfirmationEventPublisher paymentConfirmationEventPublisher, PaymentConfirmationReportEventPublisher paymentConfirmationReportEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentConfirmationEventPublisher = paymentConfirmationEventPublisher;
        this.paymentConfirmationReportEventPublisher = paymentConfirmationReportEventPublisher;
    }

    public String execute(PaymentConfirmation paymentConfirmation) {
        try {
            var paymentDataRecord = paymentRepository.findById(paymentConfirmation.id().value())
                    .orElseThrow(() -> new BusinessRuleViolationException("No payment found for id: " + paymentConfirmation.id().value()));

            paymentDataRecord.setStatus(paymentConfirmation.status().toString());
            paymentDataRecord.setConfirmedAt(paymentConfirmation.confirmedAt());
            paymentDataRecord = paymentRepository.save(paymentDataRecord);

            publishEvents(paymentConfirmation, paymentDataRecord);

            return paymentDataRecord.getId();

            //TODO: send the information to the reporting service as a payment confirmation success event
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[PaymentConfirmationService] failed to confirm payment: {}", e.getMessage(), e);
            //TODO: send the payment id and status to the student service as a payment confirmation fail event
            throw new BusinessRuleViolationException("failed to confirm payment: %s".formatted(e.getMessage()), e);
        }
    }

    private void publishEvents(final PaymentConfirmation paymentConfirmation, final PaymentDataRecord paymentDataRecord) {
        // publishes the event to the student service
        paymentConfirmationEventPublisher.publish(PaymentConfirmationEvent.builder()
                .paymentId(paymentConfirmation.id())
                .status(PaymentStatus.SUCCEEDED)
                .studentId(StudentId.of(paymentDataRecord.getStudentRegistrationId()))
                .message("Payment successful!")
                .build());

        // publishes the event to the report service
        paymentConfirmationReportEventPublisher.publish(PaymentConfirmationReportEvent.builder()
                .paymentId(paymentConfirmation.id())
                .amount(paymentDataRecord.getAmount())
                .currency(paymentDataRecord.getCurrency())
                .studentRegistrationId(StudentId.of(paymentDataRecord.getStudentRegistrationId()))
                .cardDetails(paymentConfirmation.cardDetails())
                .datetime(paymentConfirmation.confirmedAt())
                .build());
    }
}
