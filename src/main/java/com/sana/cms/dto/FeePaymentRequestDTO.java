package com.sana.cms.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FeePaymentRequestDTO {

    @NotNull(message = "Fee Structure ID is required")
    private Long feeStructureId;

    @NotNull(message = "Amount paid is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amountPaid;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;
}