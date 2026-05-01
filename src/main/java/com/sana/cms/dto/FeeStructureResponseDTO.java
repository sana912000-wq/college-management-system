package com.sana.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeStructureResponseDTO {
    private Long id;
    private String branch;
    private Integer semester;

    private BigDecimal tuitionFee;
    private BigDecimal hostelFee;
    private BigDecimal libraryFee;
    private BigDecimal labFee;

    private BigDecimal totalFee;
    private LocalDate dueDate;
}