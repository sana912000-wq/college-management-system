package com.sana.cms.dto;

import lombok.Data;

@Data
public class CourseResponseDTO {
    private Long id;
    private String facultyName;
    private String subjectName;
    private Integer semester;
    private String section;
    private String academicYear;
    private Integer totalClasses;
}
