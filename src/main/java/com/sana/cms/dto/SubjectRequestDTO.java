package com.sana.cms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectRequestDTO {

    @NotBlank(message = "Subject code is required")
    private String subjectCode;

    @NotBlank(message = "Subject name is required")
    private String subjectName;

    private String branch;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be >= 1")
    @Max(value = 8, message = "Semester cannot be more than 8")
    private Integer semester;

    @Min(value = 0, message = "Credits cannot be negative")
    private Integer credits;

    @Min(value = 0, message = "Theory marks cannot be negative")
    private Integer theoryMarks;

    @Min(value = 0, message = "Practical marks cannot be negative")
    private Integer practicalMarks;
}