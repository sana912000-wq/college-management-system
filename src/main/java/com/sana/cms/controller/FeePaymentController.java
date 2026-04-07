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

    // ✅ CREATE
    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<FeePaymentResponseDTO> create(
            @RequestBody FeePaymentRequestDTO dto) {

        return ResponseEntity.ok(feePaymentService.create(dto));
    }

    // ✅ GET ALL
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<FeePaymentResponseDTO>> getAll() {
        return ResponseEntity.ok(feePaymentService.getAll());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    public ResponseEntity<FeePaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(feePaymentService.getById(id));
    }

    // 🔥 GET BY STUDENT
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    public ResponseEntity<List<FeePaymentResponseDTO>> getByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(feePaymentService.getByStudent(studentId));
    }

    // 🔥 GET BY STATUS
    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<  List<FeePaymentResponseDTO>> getByStatus(
            @RequestParam String status) {

        return ResponseEntity.ok(feePaymentService.getByStatus(status));
    }

    // ✅ UPDATE STATUS
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FeePaymentResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(feePaymentService.updateStatus(id, status));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        feePaymentService.delete(id);
        return ResponseEntity.ok("Payment deleted successfully");
    }
}