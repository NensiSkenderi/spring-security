package com.security.learning.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS_LIST = Arrays.asList(
            new Student(1, "Nensi1"),
            new Student(2, "Nensi2"),
            new Student(3, "Nensi3")
    );

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer id) {
        return STUDENTS_LIST.stream().filter(student -> id.equals(student.getStudentId()))
                .findFirst().orElseThrow(() -> new IllegalStateException(
                        "Student " + id + " does not exists!"));
    }
}
