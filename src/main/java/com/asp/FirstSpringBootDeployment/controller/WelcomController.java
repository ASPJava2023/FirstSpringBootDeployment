package com.asp.FirstSpringBootDeployment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/welcome")
public class WelcomController {

    @GetMapping("/message")
    public String welcomeMessage() {
        log.info("Welcome message endpoint called");
        return "Welcome to Spring Boot Application! Developed by Ankit.";

    }
    @GetMapping("/author/{authorName}")
    public String welcomeToAuthor(@PathVariable String authorName) {
        log.info("Welcome message endpoint called for author: {}", authorName);
        return "Welcome "+ authorName +" to Spring Boot Application! Developed by Ankit.";

    }
}
