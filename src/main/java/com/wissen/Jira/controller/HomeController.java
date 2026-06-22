package com.wissen.Jira.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple health-check controller.
 * Useful for verifying the application has started correctly.
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Jira Clone API is running!";
    }
}
