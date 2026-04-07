package com.sana.cms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SubjectRequestDTO {

    @NotBlank
    private String subjectCode;

    @NotBlank
    private String subjectName;

    private String branch;

    private Integer semester;

    private Integer credits;

    private Integer theoryMarks;

    private Integer practicalMarks;
}

