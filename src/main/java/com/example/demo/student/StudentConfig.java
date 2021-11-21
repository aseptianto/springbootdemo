package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student asep = new Student(
                    "Asep",
                    LocalDate.of(2000, Month.JANUARY, 20),
                    "asep@iniemail.com"
            );
            Student alez = new Student(
                    "Alez",
                    LocalDate.of(1900, Month.JANUARY, 20),
                    "alez@iniemail.com"
            );

            repository.saveAll(
                    List.of(asep, alez)
            );
        };
    }
}
