package com.sana.cms.controller;

import com.sana.cms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.forgotPassword(request.get("email")));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(
                authService.resetPassword(
                        request.get("token"),
                        request.get("newPassword")
                )
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request,
                                            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(
                authService.changePassword(
                        email,
                        request.get("oldPassword"),
                        request.get("newPassword")
                )
        );
    }
}