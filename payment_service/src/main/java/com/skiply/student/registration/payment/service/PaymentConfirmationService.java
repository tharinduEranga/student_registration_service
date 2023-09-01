package com.skiply.student.registration.payment.service;

import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.payment.model.PaymentConfirmation;
import com.skiply.student.registration.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationService.class);

    private final PaymentRepository paymentRepository;

    public PaymentConfirmationService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String execute(PaymentConfirmation paymentConfirmation) {
        try {
            var paymentDataRecord = paymentRepository.findById(paymentConfirmation.id().value())
                    .orElseThrow(() -> new BusinessRuleViolationException("No payment found for id: " + paymentConfirmation.id().value()));

            paymentDataRecord.setStatus(paymentConfirmation.status().toString());
            paymentDataRecord.setConfirmedAt(paymentConfirmation.confirmedAt());
            paymentDataRecord = paymentRepository.save(paymentDataRecord);
            return paymentDataRecord.getId();

            //TODO: send the payment id and status to the student service as a payment confirmation success/fail event
            //TODO: send the information to the reporting service as a payment confirmation success/fail event
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[PaymentConfirmationService] failed to confirm payment: {}", e.getMessage(), e);
            throw new BusinessRuleViolationException("failed to confirm payment: %s".formatted(e.getMessage()), e);
        }
    }
}
