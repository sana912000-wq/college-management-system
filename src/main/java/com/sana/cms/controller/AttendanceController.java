package com.sana.cms.controller;

import com.sana.cms.dto.AttendanceRequestDTO;
import com.sana.cms.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('FACULTY')")
    public ResponseEntity<?> mark(@RequestBody @Valid AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.markAttendance(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasAnyRole('FACULTY')")
    public ResponseEntity<?> getByStudent(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getByStudent(id));
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasAnyRole('FACULTY')")
    public ResponseEntity<?> getByCourse(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getByCourse(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FACULTY')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, dto));
    }
}