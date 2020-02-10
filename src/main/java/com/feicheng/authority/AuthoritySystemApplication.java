package com.feicheng.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthoritySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthoritySystemApplication.class, args);
    }

}
