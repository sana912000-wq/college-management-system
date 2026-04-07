package com.sana.cms.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FeeStructureRequestDTO {

    private String branch;
    private Integer semester;

    private BigDecimal tuitionFee;
    private BigDecimal hostelFee;
    private BigDecimal libraryFee;
    private BigDecimal labFee;

    private LocalDate dueDate;
}
