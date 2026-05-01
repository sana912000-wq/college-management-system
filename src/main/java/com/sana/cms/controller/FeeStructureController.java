package com.sana.cms.controller;

import com.sana.cms.dto.FeeStructureRequestDTO;
import com.sana.cms.dto.FeeStructureResponseDTO;
import com.sana.cms.service.FeeStructureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fee-structures")
@RequiredArgsConstructor
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeeStructureResponseDTO> create(
            @RequestBody @Valid FeeStructureRequestDTO dto) {
        return ResponseEntity.ok(feeStructureService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<FeeStructureResponseDTO>> getAll() {
        return ResponseEntity.ok(feeStructureService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<FeeStructureResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(feeStructureService.getById(id));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<FeeStructureResponseDTO> getByBranchAndSemester(
            @RequestParam String branch,
            @RequestParam int semester) {
        return ResponseEntity.ok(
                feeStructureService.getByBranchAndSemester(branch, semester)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeeStructureResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid FeeStructureRequestDTO dto) {
        return ResponseEntity.ok(feeStructureService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        feeStructureService.delete(id);
        return ResponseEntity.ok("Fee structure deleted successfully");
    }
}
