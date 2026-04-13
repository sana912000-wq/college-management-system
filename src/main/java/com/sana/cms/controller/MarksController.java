package com.sana.cms.controller;

import com.sana.cms.dto.MarksRequestDTO;
import com.sana.cms.dto.MarksResponseDTO;
import com.sana.cms.service.MarksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marks")
@RequiredArgsConstructor
public class MarksController {

    private final MarksService marksService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY') or hasRole('ADMIN')")
    public ResponseEntity<MarksResponseDTO> createMarks(@RequestBody @Valid MarksRequestDTO dto) {
        return ResponseEntity.ok(marksService.createMarks(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MarksResponseDTO>> getAllMarks() {
        return ResponseEntity.ok(marksService.getAllMarks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<MarksResponseDTO> getMarksById(@PathVariable Long id) {
        return ResponseEntity.ok(marksService.getMarksById(id));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY') or #studentId == authentication.principal.id")
    public ResponseEntity<List<MarksResponseDTO>> getMarksByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(marksService.getMarksByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<List<MarksResponseDTO>> getMarksByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(marksService.getMarksByCourse(courseId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY') or hasRole('ADMIN')")
    public ResponseEntity<MarksResponseDTO> updateMarks(
            @PathVariable Long id,
            @RequestBody @Valid MarksRequestDTO dto) {
        return ResponseEntity.ok(marksService.updateMarks(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMarks(@PathVariable Long id) {
        marksService.deleteMarks(id);
        return ResponseEntity.ok("Marks deleted successfully");
    }
}