package com.skiply.student.registration.student.service;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.exception.DataAccessException;
import com.skiply.student.registration.student.mapper.StudentRepositoryModelMapper;
import com.skiply.student.registration.student.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudentCreator {

    private final StudentRepository studentRepository;

    public StudentCreator(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCreator.class);

    public Student execute(Student student) {
        try {
            var studentRecord = StudentRepositoryModelMapper.getStudentDataRecord(student);
            studentRecord = studentRepository.save(studentRecord);
            return StudentRepositoryModelMapper.getStudentFromDataRecord(studentRecord);
        } catch (Exception e) {
            LOGGER.error("[StudentCreator] failed to save student: {}", e.getMessage(), e);
            throw new DataAccessException("Failed to save student: %s".formatted(e.getMessage()), e);
        }
    }
}