package com.sana.cms.controller;

import com.sana.cms.dto.LoginDTO;
import com.sana.cms.dto.StudentRegisterDTO;
import com.sana.cms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegisterDTO dto) {
        return ResponseEntity.ok(studentService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(studentService.login(dto));
    }

    @GetMapping("/test")
    public String test() {
        return "Student API IS secured";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(studentService.getStudentProfile(email));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateProfile(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(
                studentService.updateStudentProfile(email, request)
        );
    }
}
