package ru.otus.nyuriv.socialnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SocialNetApp {
    public static void main(String[] args) {
        SpringApplication.run(SocialNetApp.class, args);
    }
}
