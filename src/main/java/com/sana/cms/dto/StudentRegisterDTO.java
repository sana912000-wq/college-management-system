package com.sana.cms.dto;

import lombok.Data;

@Data

public class StudentRegisterDTO {
    private String rollNumber;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String branch;
    private Integer semester;
    private Integer enrollmentYear;
    private String dob;
    private String address;
    private String city;
    private String pincode;
}
