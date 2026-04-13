package com.sana.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CourseRequestDTO {

    @NotNull
    private Long facultyId;

    @NotNull
    private Long subjectId;

    @Min(value = 1, message = "Semester must be >= 1")
    @Max(value = 8, message = "Semester must be <= 8")
    private Integer semester;

    @NotBlank(message = "Section is required")
    @Pattern(regexp = "A|B|C", message = "Section must be A, B or C")
    private String section;

    @NotBlank(message = "Academic year is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}$",
            message = "Academic year must be in format YYYY-YY (e.g., 2024-25)"
    )
    private String academicYear;

    @Min(value = 1, message = "Total classes must be >= 1")
    @Max(value = 500, message = "Total classes too large")
    private Integer totalClasses;
}