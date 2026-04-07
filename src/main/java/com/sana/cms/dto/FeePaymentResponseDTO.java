package com.sana.cms.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeePaymentResponseDTO {

    private Long id;
    private Long studentId;
    private Long feeStructureId;

    private BigDecimal amountPaid;
    private String paymentStatus;

    private String transactionId;
    private String receiptNumber;
}
