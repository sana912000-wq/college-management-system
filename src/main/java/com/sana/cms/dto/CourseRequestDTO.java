package com.sana.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotNull
    private Long facultyId;

    @NotNull
    private Long subjectId;

    @Min(1)
    private Integer semester;

    @NotBlank
    private String section;

    @NotBlank
    private String academicYear;

    @Min(1)
    private Integer totalClasses;
}
