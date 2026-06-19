package com.wissen.Jira.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class HomeController 
{
    @GetMapping("/")
    public String home()
    {
        return "Hi from GET !";
    }

    @PostMapping
    public String postHome()
    {
        return "Hi from POST !";
    }
    
}
