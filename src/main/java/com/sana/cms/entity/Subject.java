package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_code", unique = true, nullable = false)
    private String subjectCode;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    private String branch;

    private Integer semester;

    private Integer credits;

    @Column(name = "theory_marks")
    private Integer theoryMarks;

    @Column(name = "practical_marks")
    private Integer practicalMarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}