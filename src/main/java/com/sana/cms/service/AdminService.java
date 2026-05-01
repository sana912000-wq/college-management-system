package com.sana.cms.service;

import com.sana.cms.dto.JwtResponseDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.dto.AdminRegisterDTO;
import com.sana.cms.dto.RegisterResponseDTO;
import com.sana.cms.entity.Admin;
import com.sana.cms.repository.AdminRepository;
import com.sana.cms.util.JwtUtil;
import com.sana.cms.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;
    public RegisterResponseDTO register(AdminRegisterDTO dto) {

        PasswordValidator.validate(dto.getPassword());

        if (adminRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Admin already registered"
            );
        }

        try {
            Admin admin = new Admin();

            admin.setName(dto.getName());
            admin.setEmail(dto.getEmail());
            admin.setPhone(dto.getPhone());
            admin.setPasswordHash(encoder.encode(dto.getPassword()));
            admin.setRole("ADMIN");

            adminRepository.save(admin);

            RegisterResponseDTO response = new RegisterResponseDTO();
            response.setId(admin.getId());
            response.setName(admin.getName());
            response.setEmail(admin.getEmail());
            response.setRole("ADMIN");
            response.setMessage("Admin registration successful. Please login.");

            return response;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error while registering admin"
            );
        }
    }

    public JwtResponseDTO login(LoginDTO dto) {

        Admin admin = adminRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (admin == null || !encoder.matches(dto.getPassword(), admin.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        String token = JwtUtil.generateToken(
                admin.getEmail(),
                "ADMIN",
                admin.getId()
        );

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setUserId(admin.getId());
        response.setName(admin.getName());
        response.setEmail(admin.getEmail());
        response.setRole("ADMIN");

        return response;
    }

    public Map<String, Object> getAdminProfile(String email) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Admin not found"
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("id", admin.getId());
        response.put("name", admin.getName());
        response.put("email", admin.getEmail());
        response.put("role", "ADMIN");

        return response;
    }
    public Map<String, String> updateAdminProfile(String email, Map<String, String> request) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Admin not found"));

        if (request.containsKey("name")) {
            admin.setName(request.get("name"));
        }

        if (request.containsKey("phone")) {
            admin.setPhone(request.get("phone"));
        }

        adminRepository.save(admin);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin profile updated successfully");

        return response;
    }
}