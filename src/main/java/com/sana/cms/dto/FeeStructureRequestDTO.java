package com.sana.cms.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
public class FeeStructureRequestDTO {

    @NotBlank(message = "Branch is required")
    private String branch;

    @NotNull(message = "Semester is required")
    @Min(value = 0, message = "Semester cannot be negative")
    @Max(value = 8, message = "Semester cannot be greater than 8")
    private Integer semester;

    @NotNull(message = "Tuition fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tuition fee cannot be negative")
    private BigDecimal tuitionFee;

    @NotNull(message = "Hostel fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Hostel fee cannot be negative")
    private BigDecimal hostelFee;

    @NotNull(message = "Library fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Library fee cannot be negative")
    private BigDecimal libraryFee;

    @NotNull(message = "Lab fee is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Lab fee cannot be negative")
    private BigDecimal labFee;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;
}