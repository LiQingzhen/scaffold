package com.copote.coop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoopApplication.class, args);
    }

}
