package com.sample.batch.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class People {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String firstName;

    @Column(length = 20)
    private String lastName;

    @Builder
    public People(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
