package com.offer.testjava.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class CreateUserDTO {
    @NotNull(message = "Add a unique id")
    private Integer id;

    @NotBlank(message = "Please provide a first name.")
    private String firstName;

    @NotBlank(message = "Please provide a last name.")
    private String lastName;

    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Please provide an email address.")
    private String email;

    @NotNull(message = "Please provide a date.")
    @Past(message = "Please provide a past date.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    private String address;
    private String phoneNumber;


}
