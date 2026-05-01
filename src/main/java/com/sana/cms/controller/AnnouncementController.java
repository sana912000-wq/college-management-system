package com.sana.cms.controller;

import com.sana.cms.dto.AnnouncementRequestDTO;
import com.sana.cms.dto.AnnouncementResponseDTO;
import com.sana.cms.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnnouncementResponseDTO> create(
            @RequestBody AnnouncementRequestDTO dto) {
        return ResponseEntity.ok(announcementService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDTO>> getAll() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<AnnouncementResponseDTO>> getActive() {
        return ResponseEntity.ok(announcementService.getActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getById(id));
    }

    @GetMapping("/target")
    public ResponseEntity<List<AnnouncementResponseDTO>> getByTarget(
            @RequestParam String target) {
        return ResponseEntity.ok(announcementService.getByTarget(target));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnnouncementResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AnnouncementRequestDTO dto) {
        return ResponseEntity.ok(announcementService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}