package com.sana.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EnrollmentRequestDTO {

    private Long studentId;
    private Long subjectId;
    private Long courseId;

    private Integer semester;
    private String academicYear;
}

