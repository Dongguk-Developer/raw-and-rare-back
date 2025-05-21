package com.example.raw_and_rare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Main {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring!";
    }
}