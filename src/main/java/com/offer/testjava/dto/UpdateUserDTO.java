package com.offer.testjava.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @NotBlank(message = "Please provide a first name.")
    private String firstName;

    @NotBlank(message = "Please provide a last name.")
    private String lastName;

    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Please provide an email address.")
    private String email;
}
