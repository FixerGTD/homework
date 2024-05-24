package com.velsera.homework;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
        log.info("Application is Up & Running!");
    }

    @Component
    static class AppStartupRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
        }

        @PostConstruct
        public void init() {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        }
    }

}
