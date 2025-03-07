package com.umut.spring_boot_app1;


import com.umut.spring_boot_app1.entity.Students;
import com.umut.spring_boot_app1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Students> getStudents() {
        return studentService.getAllStudents();
    }
}
