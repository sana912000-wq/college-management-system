package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_structure")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branch;
    private Integer semester;

    private BigDecimal tuitionFee;
    private BigDecimal hostelFee;
    private BigDecimal libraryFee;
    private BigDecimal labFee;

    private BigDecimal totalFee;

    private LocalDate dueDate;

    private LocalDateTime createdAt = LocalDateTime.now();
}