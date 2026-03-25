package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    private String role;

    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
