package com.skiply.student.registration.payment.service;

import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.kafka.PaymentConfirmationEvent;
import com.skiply.student.registration.payment.event.PaymentConfirmationEventPublisher;
import com.skiply.student.registration.payment.model.PaymentConfirmation;
import com.skiply.student.registration.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentConfirmationEventPublisher paymentConfirmationEventPublisher;

    public PaymentConfirmationService(PaymentRepository paymentRepository, PaymentConfirmationEventPublisher paymentConfirmationEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentConfirmationEventPublisher = paymentConfirmationEventPublisher;
    }

    public String execute(PaymentConfirmation paymentConfirmation) {
        try {
            var paymentDataRecord = paymentRepository.findById(paymentConfirmation.id().value())
                    .orElseThrow(() -> new BusinessRuleViolationException("No payment found for id: " + paymentConfirmation.id().value()));

            paymentDataRecord.setStatus(paymentConfirmation.status().toString());
            paymentDataRecord.setConfirmedAt(paymentConfirmation.confirmedAt());
            paymentDataRecord = paymentRepository.save(paymentDataRecord);

            // publishes the event to the student service
            paymentConfirmationEventPublisher.publish(PaymentConfirmationEvent.builder()
                    .paymentId(paymentConfirmation.id().value())
                    .status(PaymentStatus.SUCCEEDED)
                    .studentId(paymentDataRecord.getStudentRegistrationId())
                    .message("Payment successful!")
                    .build());

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
}
