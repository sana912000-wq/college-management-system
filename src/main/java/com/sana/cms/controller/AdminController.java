package com.sana.cms.controller;

import com.sana.cms.dto.AdminRegisterDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterDTO dto) {
        return ResponseEntity.ok(adminService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(adminService.login(dto));
    }

    @GetMapping("/test")
    public String test() {
        return "Admin API IS secured";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(adminService.getAdminProfile(email));
    }
}