package com.security.learning.student;

public class Student {
    private final Integer studentId;
    private final String studentName;

    Student(Integer studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

}
