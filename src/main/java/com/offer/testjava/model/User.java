package com.offer.testjava.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import jakarta.validation.constraints.NotNull;

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
