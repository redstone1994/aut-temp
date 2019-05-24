package com.ljc.autapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AutApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutApiApplication.class, args);
    }

}
