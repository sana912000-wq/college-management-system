package com.sana.cms.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MarksRequestDTO {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Theory marks required")
    private Integer theoryMarks;

    @NotNull(message = "Practical marks required")
    private Integer practicalMarks;

    @NotNull(message = "Semester is required")
    @Min(value = 0, message = "Semester cannot be negative")
    @Max(value = 8, message = "Semester cannot be greater than 8")
    private Integer semester;

    @NotBlank(message = "Academic year is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}$",
            message = "Academic year must be in format YYYY-YY (e.g., 2024-25)"
    )
    private String academicYear;

    @AssertTrue(message = "Total marks (theory + practical) should not exceed 100")
    public boolean isTotalMarksValid() {
        if (theoryMarks == null || practicalMarks == null) return true;
        return (theoryMarks + practicalMarks) <= 100;
    }
}