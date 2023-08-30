package com.skiply.student.registration.payment.repository;

import com.skiply.student.registration.payment.repository.data.PaymentDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDataRecord, String> {

}
