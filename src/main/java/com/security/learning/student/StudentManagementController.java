package com.security.learning.student;

import org.springframework.security.access.prepost.PreAuthorize;
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

    /*
    inside @PreAuthorize("") we can put hasRole(), hasAnyRole(), hasAuthority(), hasAnyAuthority()
     */

    // use permission based auth on method level
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINEE')")
    public List<Student> getAllStudents(){
        return STUDENTS_LIST;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void addNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") Integer studentId){
        System.out.println(studentId);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("id") Integer studentId, @RequestBody Student student){
        System.out.println(String.format("%s %s", studentId, student));
    }
}
