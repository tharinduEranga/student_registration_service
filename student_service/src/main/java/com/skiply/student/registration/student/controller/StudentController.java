package com.skiply.student.registration.student.controller;

import com.skiply.student.registration.student.StudentsApi;
import com.skiply.student.registration.student.mapper.StudentApiModelMapper;
import com.skiply.student.registration.student.model.GetStudentByIdResponse;
import com.skiply.student.registration.student.model.StudentCreateRequest;
import com.skiply.student.registration.student.model.StudentCreateResponse;
import com.skiply.student.registration.student.service.StudentCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController implements StudentsApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentCreator studentCreator;

    public StudentController(StudentCreator studentCreator) {
        this.studentCreator = studentCreator;
    }

    @Override
    public ResponseEntity<StudentCreateResponse> createStudent(StudentCreateRequest studentCreateRequest) {
        LOGGER.info("Create student request: {}", studentCreateRequest);
        var student = StudentApiModelMapper.getStudentFromCreateRequest(studentCreateRequest);
        student = studentCreator.execute(student);
        return new ResponseEntity<>(StudentApiModelMapper.getCreateRequestFromStudent(student), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GetStudentByIdResponse> getStudentById(String studentId) {
        return null;
    }
}
