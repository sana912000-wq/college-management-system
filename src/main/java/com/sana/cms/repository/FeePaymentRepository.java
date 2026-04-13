package com.sana.cms.repository;

import com.sana.cms.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findByStudentId(Long studentId);

    List<FeePayment> findByPaymentStatus(String status);
}