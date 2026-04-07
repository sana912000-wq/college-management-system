package com.sana.cms.service;

import com.sana.cms.dto.FeePaymentRequestDTO;
import com.sana.cms.dto.FeePaymentResponseDTO;
import com.sana.cms.entity.FeePayment;
import com.sana.cms.entity.FeeStructure;
import com.sana.cms.entity.Student;
import com.sana.cms.repository.FeePaymentRepository;
import com.sana.cms.repository.FeeStructureRepository;
import com.sana.cms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final StudentRepository studentRepository;
    private final FeeStructureRepository feeStructureRepository;

    // ✅ CREATE PAYMENT
    public FeePaymentResponseDTO create(FeePaymentRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        FeeStructure feeStructure = feeStructureRepository.findById(dto.getFeeStructureId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Fee structure not found"));

        FeePayment payment = new FeePayment();

        payment.setStudent(student);
        payment.setFeeStructure(feeStructure);
        payment.setAmountPaid(dto.getAmountPaid());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());

        // 🔥 status logic
        if (dto.getAmountPaid().compareTo(feeStructure.getTotalFee()) >= 0) {
            payment.setPaymentStatus("COMPLETED");
        } else {
            payment.setPaymentStatus("PENDING");
        }

        payment.setReceiptNumber("RCPT-" + System.currentTimeMillis());
        payment.setCreatedAt(LocalDateTime.now());

        return mapToDTO(feePaymentRepository.save(payment));
    }

    // ✅ GET ALL
    public List<FeePaymentResponseDTO> getAll() {
        return feePaymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ GET BY ID
    public FeePaymentResponseDTO getById(Long id) {
        FeePayment payment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"));

        return mapToDTO(payment);
    }

    // 🔥 GET BY STUDENT (VERY IMPORTANT)
    public List<FeePaymentResponseDTO> getByStudent(Long studentId) {
        return feePaymentRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // 🔥 GET BY STATUS
    public List<FeePaymentResponseDTO> getByStatus(String status) {
        return feePaymentRepository.findByPaymentStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ UPDATE STATUS
    public FeePaymentResponseDTO updateStatus(Long id, String status) {

        FeePayment payment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"));

        payment.setPaymentStatus(status);

        return mapToDTO(feePaymentRepository.save(payment));
    }

    // ✅ DELETE
    public void delete(Long id) {
        if (!feePaymentRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Payment not found");
        }

        feePaymentRepository.deleteById(id);
    }

    // 🔁 MAPPING
    private FeePaymentResponseDTO mapToDTO(FeePayment p) {

        FeePaymentResponseDTO dto = new FeePaymentResponseDTO();

        dto.setId(p.getId());
        dto.setStudentId(p.getStudent().getId());
        dto.setFeeStructureId(p.getFeeStructure().getId());
        dto.setAmountPaid(p.getAmountPaid());
        dto.setPaymentStatus(p.getPaymentStatus());
        dto.setTransactionId(p.getTransactionId());
        dto.setReceiptNumber(p.getReceiptNumber());

        return dto;
    }
}