package com.skiply.student.registration.student.mapper;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.StudentGrade;
import com.skiply.student.registration.common.model.StudentStatus;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.student.repository.data.StudentDataRecord;

public class StudentRepositoryModelMapper {
    private StudentRepositoryModelMapper() {
    }

    public static StudentDataRecord getStudentDataRecord(Student student) {
        return StudentDataRecord.builder()
                .id(student.id().value())
                .name(student.name())
                .grade(student.grade().toString())
                .mobile(student.mobile())
                .status(student.status().name())
                .school(student.school())
                .build();
    }

    public static Student getStudentFromDataRecord(StudentDataRecord dataRecord) {
        return new Student(
                StudentId.of(dataRecord.getId()),
                dataRecord.getName(),
                StudentGrade.fromString(dataRecord.getGrade()),
                dataRecord.getMobile(),
                StudentStatus.valueOf(dataRecord.getStatus()),
                dataRecord.getSchool()
        );
    }
}
