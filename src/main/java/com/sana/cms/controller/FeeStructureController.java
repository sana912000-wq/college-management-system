package com.sana.cms.controller;

import com.sana.cms.dto.FeeStructureRequestDTO;
import com.sana.cms.dto.FeeStructureResponseDTO;
import com.sana.cms.service.FeeStructureService;
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

    // ✅ CREATE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeeStructureResponseDTO> create(
            @RequestBody FeeStructureRequestDTO dto) {

        return ResponseEntity.ok(feeStructureService.create(dto));
    }

    // ✅ GET ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    public ResponseEntity<List<FeeStructureResponseDTO>> getAll() {
        return ResponseEntity.ok(feeStructureService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    public ResponseEntity<FeeStructureResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(feeStructureService.getById(id));
    }

    // 🔥 GET BY BRANCH + SEMESTER (VERY IMPORTANT)
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<FeeStructureResponseDTO> getByBranchAndSemester(
            @RequestParam String branch,
            @RequestParam int semester) {

        return ResponseEntity.ok(
                feeStructureService.getByBranchAndSemester(branch, semester)
        );
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FeeStructureResponseDTO> update(
            @PathVariable Long id,
            @RequestBody FeeStructureRequestDTO dto) {

        return ResponseEntity.ok(feeStructureService.update(id, dto));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        feeStructureService.delete(id);
        return ResponseEntity.ok("Fee structure deleted successfully");
    }
}
