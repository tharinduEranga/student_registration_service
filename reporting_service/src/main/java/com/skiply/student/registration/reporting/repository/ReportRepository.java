package com.skiply.student.registration.reporting.repository;

import com.skiply.student.registration.reporting.repository.data.ReportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportRecord, String> {
    List<ReportRecord> findByPaymentId(String paymentId);
}
