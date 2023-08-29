package com.skiply.student.registration.student.service;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.exception.DataAccessException;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.student.mapper.StudentRepositoryModelMapper;
import com.skiply.student.registration.student.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudentFetchService {

    private final StudentRepository studentRepository;

    public StudentFetchService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentFetchService.class);

    public Student getStudentById(StudentId studentId) {
        try {
            var studentRecord = studentRepository.findById(studentId.value())
                    .orElseThrow(() -> new BusinessRuleViolationException("Student not found for the id: " + studentId.value()));
            return StudentRepositoryModelMapper.getStudentFromDataRecord(studentRecord);
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[StudentCreator] failed to get student by id: {}", e.getMessage(), e);
            throw new DataAccessException("Failed to get student by id: %s".formatted(e.getMessage()), e);
        }
    }
}
