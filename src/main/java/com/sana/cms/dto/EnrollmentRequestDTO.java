package com.sana.cms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EnrollmentRequestDTO {
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot be more than 8")
    private Integer semester;

    @NotBlank(message = "Academic year is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}$",
            message = "Academic year must be in format YYYY-YY (e.g., 2024-25)"
    )
    private String academicYear;
}