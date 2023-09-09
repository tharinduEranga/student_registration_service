package com.skiply.student.registration.student.mapper;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.StudentGrade;
import com.skiply.student.registration.common.model.StudentStatus;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.student.model.GetStudentByIdResponse;
import com.skiply.student.registration.student.model.StudentCreateRequest;
import com.skiply.student.registration.student.model.StudentCreateResponse;
import com.skiply.student.registration.student.model.StudentCreatedData;

public class StudentApiModelMapper {

    private StudentApiModelMapper() {
    }

    public static Student getStudentFromCreateRequest(StudentCreateRequest request) {
        return Student.builder()
                .id(StudentId.random())
                .name(request.getName())
                .grade(StudentGrade.fromString(request.getGrade()))
                .mobile(request.getMobile())
                .status(StudentStatus.PENDING_REGISTRATION)
                .school(request.getSchool())
                .build();
    }

    public static StudentCreateResponse getCreateResponseFromStudent(StudentCreatedData studentCreatedData) {
        return new StudentCreateResponse()
                .id(studentCreatedData.student().id().value())
                .name(studentCreatedData.student().name())
                .grade(String.valueOf(studentCreatedData.student().grade().value()))
                .mobile(studentCreatedData.student().mobile())
                .status(StudentCreateResponse.StatusEnum.valueOf(studentCreatedData.student().status().toString()))
                .school(studentCreatedData.student().school())
                .paymentId(studentCreatedData.paymentId().value());
    }

    public static GetStudentByIdResponse getStudentResponseFromStudent(Student student) {
        return new GetStudentByIdResponse()
                .id(student.id().value())
                .name(student.name())
                .grade(String.valueOf(student.grade().value()))
                .mobile(student.mobile())
                .status(GetStudentByIdResponse.StatusEnum.valueOf(student.status().toString()))
                .school(student.school());
    }
}
