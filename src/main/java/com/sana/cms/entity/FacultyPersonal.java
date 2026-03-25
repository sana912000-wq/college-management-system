package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "faculty_personal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FacultyPersonal {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "password_hash")
    private String passwordHash;

    private String designation;

    private String department;

    private String qualification;

    @Column(name = "expierence_years")
    private int expierenceYears;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

