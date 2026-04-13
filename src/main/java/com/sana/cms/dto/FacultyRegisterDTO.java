package com.sana.cms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FacultyRegisterDTO {
    @NotBlank(message = "Name is Required")
    private String name;

    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Phone is Required")
    private String phone;

    @NotBlank(message = "Password is Required")
    private String password;

    private String designation;

    private String department;

}
