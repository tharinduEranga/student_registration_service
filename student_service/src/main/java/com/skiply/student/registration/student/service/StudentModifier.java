package com.skiply.student.registration.student.service;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.StudentStatus;
import com.skiply.student.registration.common.model.exception.BusinessRuleViolationException;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.student.mapper.StudentRepositoryModelMapper;
import com.skiply.student.registration.student.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentModifier {

    private final StudentRepository studentRepository;
    private final StudentFetchService studentFetchService;

    public StudentModifier(StudentRepository studentRepository, StudentFetchService studentFetchService) {
        this.studentRepository = studentRepository;
        this.studentFetchService = studentFetchService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentModifier.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public Student updateStatus(StudentId id, StudentStatus status) {
        try {
            var student = studentFetchService.getStudentById(id);
            var studentRecord = StudentRepositoryModelMapper.getStudentDataRecord(student);
            studentRecord.setStatus(status.toString());
            studentRecord = studentRepository.save(studentRecord);
            return StudentRepositoryModelMapper.getStudentFromDataRecord(studentRecord);
        } catch (BusinessRuleViolationException e) {
            throw e; //explicitly catch because the error needs to be distinguished from other errors
        } catch (Exception e) {
            LOGGER.error("[StudentModifier] failed to update student: {}", e.getMessage(), e);
            throw new BusinessRuleViolationException("Failed to update student: %s".formatted(e.getMessage()), e);
        }
    }

}
