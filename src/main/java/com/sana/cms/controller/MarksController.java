package com.sana.cms.controller;

import com.sana.cms.dto.MarksRequestDTO;
import com.sana.cms.dto.MarksResponseDTO;
import com.sana.cms.service.MarksService;
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

    // ✅ CREATE
    @PostMapping
    @PreAuthorize("hasRole('FACULTY') or hasRole('ADMIN')")
    public ResponseEntity<MarksResponseDTO> createMarks(@RequestBody MarksRequestDTO dto) {
        return ResponseEntity.ok(marksService.createMarks(dto));
    }

    // ✅ GET ALL
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MarksResponseDTO>> getAllMarks() {
        return ResponseEntity.ok(marksService.getAllMarks());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<MarksResponseDTO> getMarksById(@PathVariable Long id) {
        return ResponseEntity.ok(marksService.getMarksById(id));
    }

    // ✅ GET BY STUDENT
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<List<MarksResponseDTO>> getMarksByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(marksService.getMarksByStudent(studentId));
    }

    // ✅ GET BY COURSE
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<List<MarksResponseDTO>> getMarksByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(marksService.getMarksByCourse(courseId));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY') or hasRole('ADMIN')")
    public ResponseEntity<MarksResponseDTO> updateMarks(
            @PathVariable Long id,
            @RequestBody MarksRequestDTO dto) {

        return ResponseEntity.ok(marksService.updateMarks(id, dto));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMarks(@PathVariable Long id) {
        marksService.deleteMarks(id);
        return ResponseEntity.ok("Marks deleted successfully");
    }
}