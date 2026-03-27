package com.sana.cms.controller;

import com.sana.cms.dto.FacultyRegisterDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
