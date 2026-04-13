package com.sana.cms.controller;

import com.sana.cms.dto.FeePaymentRequestDTO;
import com.sana.cms.dto.FeePaymentResponseDTO;
import com.sana.cms.service.FeePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fee-payments")
@RequiredArgsConstructor
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<FeePaymentResponseDTO> create(
            @RequestBody FeePaymentRequestDTO dto) {
        return ResponseEntity.ok(feePaymentService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')") // Only admin and faculty can see all payments
    public ResponseEntity<List<FeePaymentResponseDTO>> getAll() {
        return ResponseEntity.ok(feePaymentService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<FeePaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(feePaymentService.getById(id));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')  or #studentId == authentication.principal.id")
    public ResponseEntity<List<FeePaymentResponseDTO>> getByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(feePaymentService.getByStudent(studentId));
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<  List<FeePaymentResponseDTO>> getByStatus(
            @RequestParam String status) {
        return ResponseEntity.ok(feePaymentService.getByStatus(status));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeePaymentResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(feePaymentService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        feePaymentService.delete(id);
        return ResponseEntity.ok("Payment deleted successfully");
    }
}