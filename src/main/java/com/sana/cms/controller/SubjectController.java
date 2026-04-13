package com.sana.cms.controller;

import com.sana.cms.dto.SubjectRequestDTO;
import com.sana.cms.service.SubjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/admin/subjects")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody @Valid SubjectRequestDTO dto) {
        return ResponseEntity.ok(subjectService.createSubject(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid SubjectRequestDTO dto) {
        return ResponseEntity.ok(subjectService.updateSubject(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getByBranchAndSemester(
            @RequestParam @NotBlank String branch,
            @RequestParam @Min(1) @Max(8) int semester) {

        return ResponseEntity.ok(
                subjectService.getByBranchAndSemester(branch, semester)
        );
    }
}
