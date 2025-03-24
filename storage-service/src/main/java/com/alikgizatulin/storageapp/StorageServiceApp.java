package com.alikgizatulin.storageapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alikgizatulin.storageapp","com.alikgizatulin.commonlibrary"})
public class StorageServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApp.class,args);
    }
}
