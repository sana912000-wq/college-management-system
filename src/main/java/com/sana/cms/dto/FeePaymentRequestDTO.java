package com.sana.cms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeePaymentRequestDTO {

    private Long studentId;
    private Long feeStructureId;

    private BigDecimal amountPaid;

    private String transactionId;
}
