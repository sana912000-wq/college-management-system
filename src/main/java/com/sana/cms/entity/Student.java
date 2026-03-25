package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roll_number", unique = true)
    private String rollNumber;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "password_hash")
    private String passwordHash;

    private String branch;

    private Integer semester;

    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    private LocalDate dob;

    private String address;

    private String city;

    private String pincode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}