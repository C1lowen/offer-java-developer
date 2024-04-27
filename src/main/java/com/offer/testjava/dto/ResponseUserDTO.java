package com.offer.testjava.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseUserDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Date date;
    private String address;
    private String phoneNumber;

}
