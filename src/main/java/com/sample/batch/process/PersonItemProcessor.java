package com.sample.batch.process;

import com.sample.batch.entity.People;
import com.sample.batch.payload.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, People> {

    @Override
    public People process(Person item) throws Exception {

        final String firstName = item.getFirstName().toUpperCase();
        final String lastName = item.getLastName().toUpperCase();

        final People people = People.builder().firstName(firstName).lastName(lastName).build();

        return people;
    }
}
