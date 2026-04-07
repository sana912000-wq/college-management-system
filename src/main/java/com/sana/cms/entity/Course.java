package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private FacultyPersonal faculty;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private Integer semester;

    private String section;

    private String academicYear;

    private Integer totalClasses;

    private LocalDateTime createdAt;
}
