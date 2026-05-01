package com.sana.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MarksResponseDTO {

    private Long id;
    private Long studentId;
    private Long subjectId;
    private Long courseId;

    private Integer theoryMarks;
    private Integer practicalMarks;
    private Integer totalMarks;

    private String grade;

    private Integer semester;
    private String academicYear;
}