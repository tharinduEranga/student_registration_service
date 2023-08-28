package com.skiply.student.registration.student.repository;

import com.skiply.student.registration.student.repository.data.StudentDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentDataRecord, String> {

}
