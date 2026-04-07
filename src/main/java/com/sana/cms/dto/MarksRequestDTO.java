package com.sana.cms.dto;

import lombok.Data;

@Data
public class MarksRequestDTO {

    private Long studentId;
    private Long subjectId;
    private Long courseId;

    private Integer theoryMarks;
    private Integer practicalMarks;

    private Integer semester;
    private String academicYear;
}
