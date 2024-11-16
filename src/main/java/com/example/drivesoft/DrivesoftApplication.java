package com.example.drivesoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The {@code DrivesoftApplication} class serves as the entry point for the Spring Boot application.
 * It is annotated with {@code @SpringBootApplication}, which is a convenience annotation that combines:
 * <ul>
 *   <li>{@code @Configuration} - Marks this class as a source of bean definitions for the application context.</li>
 *   <li>{@code @EnableAutoConfiguration} - Tells Spring Boot to automatically configure the application based on the dependencies present.</li>
 *   <li>{@code @ComponentScan} - Tells Spring to scan for components, configurations, and services in the current package and its subpackages.</li>
 * </ul>
 *
 * <p>This class also includes the {@code @EnableScheduling} annotation, enabling the scheduling of tasks within the application.
 * It is used to mark the class as capable of running scheduled tasks (e.g., periodic tasks, cron jobs, etc.).</p>
 *
 * <p>Upon running, the application will bootstrap the Spring context and initialize the entire application.</p>
 *
 * @since 1.0
 */
@SpringBootApplication
@EnableScheduling
public class DrivesoftApplication {

  /**
   * The main method is the entry point of the Spring Boot application.
   * It calls {@link SpringApplication#run(Class, String...)} to launch the application.
   *
   * @param args command-line arguments passed during application startup
   */
  public static void main(String[] args) {
    SpringApplication.run(DrivesoftApplication.class, args);
  }

}
