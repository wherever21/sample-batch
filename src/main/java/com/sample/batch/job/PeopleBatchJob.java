package com.sample.batch.job;


import com.sample.batch.entity.People;
import com.sample.batch.entity.PeopleRepository;
import com.sample.batch.payload.Person;
import com.sample.batch.process.PersonItemProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PeopleBatchJob {

    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final PeopleRepository peopleRepository;

    @StepScope
    @Bean
    public FlatFileItemReader<Person> personItemReader() {

        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }})
                .build();

    }

    @Bean
    public PersonItemProcessor personItemProcessor() {
        return new PersonItemProcessor();
    }

    @StepScope
    @Bean
    public JpaItemWriter<People> personItemWriter() {

        return new JpaItemWriterBuilder<People>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, People>chunk(10)
                .reader(personItemReader())
                .processor(personItemProcessor())
                .writer(personItemWriter())
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserjob")
                .listener(importJobExecutionListener())
                .incrementer(new RunIdIncrementer())
                .flow(step1())

                .end().build();
    }

    @Bean
    public JobExecutionListener importJobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {

            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

                    peopleRepository.findAll().forEach(people -> log.info("People : {},  {}", people.getId(), people.getFirstName() + " " + people.getLastName()));

                }
            }
        };
    }


}


