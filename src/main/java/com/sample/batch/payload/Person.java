package com.sample.batch.payload;


import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {

    private static final long serialVersionUID = -5587711260904299718L;

    private Long id;
    private String firstName;
    private String lastName;
}
