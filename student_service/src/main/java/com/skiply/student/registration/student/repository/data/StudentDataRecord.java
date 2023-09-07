package com.skiply.student.registration.student.repository.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class StudentDataRecord {
    @Id
    private String id;
    private String name;
    private String grade;
    private String mobile;
    private String status;
    private String school;

    public StudentDataRecord() {
        // Default constructor
    }

    public StudentDataRecord(String id, String name, String grade, String mobile, String status, String school) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.mobile = mobile;
        this.status = status;
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Builder {
        private String id;
        private String name;
        private String grade;
        private String mobile;
        private String status;
        private String school;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder grade(String grade) {
            this.grade = grade;
            return this;
        }

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder school(String school) {
            this.school = school;
            return this;
        }

        public StudentDataRecord build() {
            return new StudentDataRecord(id, name, grade, mobile, status, school);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
