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
    public ResponseEntity<?> mark(@RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.markAttendance(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getByStudent(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getByStudent(id));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getByCourse(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getByCourse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, dto));
    }
}