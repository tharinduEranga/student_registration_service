package com.skiply.student.registration.payment.service;

import com.skiply.student.registration.common.model.Payment;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.payment.externalintegration.PaymentGateway;
import com.skiply.student.registration.payment.externalintegration.model.PaymentInitiationGatewayRequest;
import com.skiply.student.registration.payment.mapper.PaymentRepositoryModelMapper;
import com.skiply.student.registration.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class PaymentInitiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInitiator.class);

    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    public PaymentInitiator(PaymentRepository paymentRepository, PaymentGateway paymentGateway) {
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
    }

    @Transactional
    public String execute(Payment payment) {
        try {
            //call payment gateway
            var paymentResponse = paymentGateway.pay(PaymentInitiationGatewayRequest.builder()
                    .amount(payment.amount())
                    .dateTime(OffsetDateTime.now())
                    .description("initiate payment for: %s".formatted(payment.studentRegistrationId()))
                    .metaData(payment.metadata())
                    .reference(payment.id().value())
                    .build());

            //map the payment to the database object
            var paymentDataRecord = PaymentRepositoryModelMapper.getPaymentDataRecordFromPayment(payment);
            paymentDataRecord.setPaymentIdFromGateway(paymentResponse.id());
            paymentDataRecord = paymentRepository.save(paymentDataRecord);

            //return the saved id
            payment = PaymentRepositoryModelMapper.getPaymentFromPaymentDataRecord(paymentDataRecord);
            return payment.id().value();

        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[PaymentInitiator] failed to save payment initiation: {}", e.getMessage(), e);
            throw new BusinessRuleViolationException("failed to save payment initiation: %s".formatted(e.getMessage()), e);
        }
    }
}
