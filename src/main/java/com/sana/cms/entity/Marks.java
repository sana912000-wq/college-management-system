package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "marks")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Course course;

    private Integer theoryMarks;
    private Integer practicalMarks;

    private Integer totalMarks;

    private String grade;

    private Integer semester;
    private String academicYear;

    private LocalDateTime updatedAt = LocalDateTime.now();
}