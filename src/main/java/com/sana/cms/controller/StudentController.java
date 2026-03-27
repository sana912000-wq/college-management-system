package com.sana.cms.controller;

import com.sana.cms.dto.FacultyRegisterDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.dto.StudentRegisterDTO;
import com.sana.cms.entity.Student;
import com.sana.cms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
