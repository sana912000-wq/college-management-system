package com.sana.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceRequestDTO {

    @NotNull(message = "Student ID is required")
    @Positive(message = "Student ID must be positive")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Long courseId;

    @NotNull(message = "Class date is required")
    @PastOrPresent(message = "Class date cannot be in future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate classDate;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "PRESENT|ABSENT",
            message = "Status must be either PRESENT or ABSENT"
    )
    private String status;
}
