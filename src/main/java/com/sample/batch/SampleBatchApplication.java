package com.sample.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableTask
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class SampleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleBatchApplication.class, args);
    }

}
