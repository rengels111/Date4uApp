package com.test.springtest;

import com.test.springtest.controller.WebTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringTestApplication {

    public static void main( String[] args ) {
        ConfigurableApplicationContext ctx = SpringApplication.run( SpringTestApplication.class, args );
    }

}
