package com.umut.spring_boot_app1.service;


import com.umut.spring_boot_app1.entity.Students;
import com.umut.spring_boot_app1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Students> getAllStudents() {
        return studentRepository.findAll();
    }
}
