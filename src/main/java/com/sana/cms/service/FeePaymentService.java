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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final StudentRepository studentRepository;
    private final FeeStructureRepository feeStructureRepository;

    public FeePaymentResponseDTO create(FeePaymentRequestDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"
                ));

        FeeStructure feeStructure = feeStructureRepository.findById(dto.getFeeStructureId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Fee structure not found"
                ));

        FeePayment payment = new FeePayment();

        payment.setStudent(student);
        payment.setFeeStructure(feeStructure);
        payment.setAmountPaid(dto.getAmountPaid());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());

        if (dto.getAmountPaid().compareTo(feeStructure.getTotalFee()) >= 0) {
            payment.setPaymentStatus("COMPLETED");
        } else {
            payment.setPaymentStatus("PENDING");
        }

        payment.setReceiptNumber("RCPT-" + System.currentTimeMillis());
        payment.setCreatedAt(LocalDateTime.now());

        return mapToDTO(feePaymentRepository.save(payment));
    }

    public List<FeePaymentResponseDTO> getAll() {
        return feePaymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FeePaymentResponseDTO getById(Long id) {
        FeePayment payment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"
                ));
        return mapToDTO(payment);
    }

    public List<FeePaymentResponseDTO> getByStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            return new ArrayList<>(); // no error, empty response
        }

        return feePaymentRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<FeePaymentResponseDTO> getByStatus(String status) {
        return feePaymentRepository.findByPaymentStatus(status)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FeePaymentResponseDTO updateStatus(Long id, String status) {
        FeePayment payment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment not found"
                ));

        payment.setPaymentStatus(status);

        return mapToDTO(feePaymentRepository.save(payment));
    }

    public void delete(Long id) {
        feePaymentRepository.deleteById(id);
    }

    private FeePaymentResponseDTO mapToDTO(FeePayment payment) {

        FeePaymentResponseDTO dto = new FeePaymentResponseDTO();

        dto.setId(payment.getId());
        dto.setStudentId(payment.getStudent().getId());
        dto.setFeeStructureId(payment.getFeeStructure().getId());
        dto.setAmountPaid(payment.getAmountPaid());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setReceiptNumber(payment.getReceiptNumber());

        return dto;
    }
}