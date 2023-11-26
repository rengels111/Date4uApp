package com.test.springtest.core;

import org.springframework.stereotype.Service;

@Service
public class HelloName {

    private String name = "Java";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
