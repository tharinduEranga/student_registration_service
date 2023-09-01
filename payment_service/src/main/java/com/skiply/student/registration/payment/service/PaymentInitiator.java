package com.skiply.student.registration.payment.service;

import com.skiply.student.registration.common.model.Payment;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.payment.mapper.PaymentRepositoryModelMapper;
import com.skiply.student.registration.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentInitiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInitiator.class);

    private final PaymentRepository paymentRepository;

    public PaymentInitiator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String execute(Payment payment) {
        try {
            var paymentDataRecord = PaymentRepositoryModelMapper.getPaymentDataRecordFromPayment(payment);
            paymentDataRecord = paymentRepository.save(paymentDataRecord);
            payment = PaymentRepositoryModelMapper.getPaymentFromPaymentDataRecord(paymentDataRecord);
            //TODO: call the gateway and get the id and update the record
            //TODO: send the payment id to the student service as a payment initiation success/fail event
            return payment.id().value();
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[PaymentInitiator] failed to save payment initiation: {}", e.getMessage(), e);
            throw new BusinessRuleViolationException("failed to save payment initiation: %s".formatted(e.getMessage()), e);
        }
    }
}
