package com.offer.testjava.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Date date;
    private String address;
    private String phoneNumber;
}
