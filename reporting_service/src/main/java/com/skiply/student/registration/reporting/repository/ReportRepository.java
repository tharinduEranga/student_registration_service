package com.skiply.student.registration.reporting.repository;

import com.skiply.student.registration.reporting.repository.data.ReportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportRecord, String> {
}
