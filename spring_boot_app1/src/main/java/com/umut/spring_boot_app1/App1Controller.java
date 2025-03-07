package com.umut.spring_boot_app1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class App1Controller {

    @GetMapping
    public String home() {
        return "Spring Boot App is running!";
    }

}
