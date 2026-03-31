package com.sana.cms.controller;

import com.sana.cms.dto.FacultyRegisterDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFaculty(@Valid @RequestBody FacultyRegisterDTO dto) {
        return ResponseEntity.ok(facultyService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginFaculty(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(facultyService.login(dto));
    }
    @GetMapping("/test")
    public String test() {
        return "Faculty API IS secured";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(facultyService.getFacultyProfile(email));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<?> updateProfile(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(
                facultyService.updateFacultyProfile(email, request)
        );
    }
}
