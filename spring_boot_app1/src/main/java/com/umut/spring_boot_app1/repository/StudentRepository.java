package com.umut.spring_boot_app1.repository;

import com.umut.spring_boot_app1.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Long> {
}
