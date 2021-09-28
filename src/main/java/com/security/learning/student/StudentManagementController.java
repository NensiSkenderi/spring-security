package com.security.learning.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS_LIST = Arrays.asList(
            new Student(1, "Nensi1"),
            new Student(2, "Nensi2"),
            new Student(3, "Nensi3")
    );

    @GetMapping
    public List<Student> getAllStudents(){
        return STUDENTS_LIST;
    }

    @PostMapping
    public void addNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") Integer studentId){
        System.out.println(studentId);
    }

    @PutMapping("{id}")
    public void updateStudent(@PathVariable("id") Integer studentId, @RequestBody Student student){
        System.out.println(String.format("%s %s", studentId, student));
    }
}
