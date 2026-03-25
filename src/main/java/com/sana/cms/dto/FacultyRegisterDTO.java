package com.sana.cms.dto;

import lombok.Data;

@Data

public class FacultyRegisterDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String designation;
    private String department;
    private String qualification;
    private Integer experienceYears;
}
