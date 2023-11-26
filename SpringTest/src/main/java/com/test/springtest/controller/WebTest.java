package com.test.springtest.controller;

import com.test.springtest.core.HelloName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebTest {

    private final HelloName helloName;

    @Autowired
    WebTest(HelloName helloName) {
        this.helloName = helloName;
    }

    private static final String template = "Hello, %s!";

    @GetMapping("/test")
    public String test( @RequestParam(value = "name") String name ) {
        return String.format(template, name);
    }

    @GetMapping("/")
    public String home() {
        return helloName.getName();
    }


}
