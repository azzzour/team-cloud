package com.alikgizatulin.teamapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alikgizatulin.teamapp","com.alikgizatulin.commonlibrary"})
public class TeamServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(TeamServiceApp.class, args);
    }
}
