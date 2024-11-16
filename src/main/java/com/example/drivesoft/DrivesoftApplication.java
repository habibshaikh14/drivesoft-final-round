package com.example.drivesoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DrivesoftApplication {

  public static void main(String[] args) {
    SpringApplication.run(DrivesoftApplication.class, args);
  }

}
