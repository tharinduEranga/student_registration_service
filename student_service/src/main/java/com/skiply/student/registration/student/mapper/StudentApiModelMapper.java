package com.skiply.student.registration.student.mapper;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.StudentGrade;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.student.model.StudentCreateRequest;
import com.skiply.student.registration.student.model.StudentCreateResponse;

public class StudentApiModelMapper {

    private StudentApiModelMapper() {
    }

    public static Student getStudentFromCreateRequest(StudentCreateRequest request) {
        return Student.builder()
                .id(StudentId.random())
                .name(request.getName())
                .grade(StudentGrade.fromString(request.getGrade()))
                .mobile(request.getMobile())
                .school(request.getSchool())
                .build();
    }

    public static StudentCreateResponse getCreateRequestFromStudent(Student student) {
        return new StudentCreateResponse()
                .id(student.id().value())
                .name(student.name())
                .grade(String.valueOf(student.grade().value()))
                .mobile(student.mobile())
                .school(student.school());
    }
}
