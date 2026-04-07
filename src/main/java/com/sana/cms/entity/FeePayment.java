package com.sana.cms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private FeeStructure feeStructure;

    private BigDecimal amountPaid;

    private LocalDateTime paymentDate;

    private String transactionId;

    private String paymentStatus; // PENDING, COMPLETED, FAILED

    @Column(unique = true)
    private String receiptNumber;

    private LocalDateTime createdAt = LocalDateTime.now();
}
