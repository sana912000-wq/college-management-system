package com.sana.cms.service;

import com.sana.cms.dto.FacultyRegisterDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.entity.FacultyPersonal;
import com.sana.cms.repository.FacultyPersonalRepository;
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
public class FacultyService {

    @Autowired
    private FacultyPersonalRepository facultyPersonalRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Map<String,Object> register(FacultyRegisterDTO dto) {

        PasswordValidator. validate(dto.getPassword());

        if (facultyPersonalRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        try {
            FacultyPersonal faculty = new FacultyPersonal();

            faculty.setName(dto.getName());
            faculty.setEmail(dto.getEmail());
            faculty.setPhone(dto.getPhone());
            faculty.setPasswordHash(encoder.encode(dto.getPassword()));
            faculty.setDepartment(dto.getDepartment());
            faculty.setDesignation(dto.getDesignation());

            facultyPersonalRepository.save(faculty);

            Map<String, Object> response = new HashMap<>();
            response.put("id", faculty.getId());
            response.put("name", faculty.getName());
            response.put("email", faculty.getEmail());
            response.put("role", "FACULTY");
            response.put("message", "Registration successful. Please login.");

            return response;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }


    public Map<String,Object> login(LoginDTO dto) {
        FacultyPersonal faculty = facultyPersonalRepository
                .findByEmail(dto.getEmail())
                .orElse(null);

        if (faculty == null || !encoder.matches(dto.getPassword(), faculty.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String token = JwtUtil.generateToken(
                faculty.getEmail(),
                "FACULTY",
                faculty.getId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("userId", faculty.getId());
        response.put("email", faculty.getEmail());
        response.put("name", faculty.getName());
        response.put("role", "FACULTY");

        return response;
    }

    public Map<String, Object> getFacultyProfile(String email) {

        FacultyPersonal faculty = facultyPersonalRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Faculty not found"
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("name", faculty.getName());
        response.put("phone", faculty.getPhone());
        response.put("email", faculty.getEmail());
        response.put("department", faculty.getDepartment());
        response.put("designation", faculty.getDesignation());
        response.put("qualification", faculty.getQualification());
        response.put("expierenceYears", faculty.getExpierenceYears());
        response.put("role", "FACULTY");

        return response;
    }
    public Map<String, String> updateFacultyProfile(String email, Map<String, String> request) {

        FacultyPersonal faculty = facultyPersonalRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Faculty not found"));

        if (request.containsKey("name")) {
            faculty.setName(request.get("name"));
        }

        if (request.containsKey("phone")) {
            faculty.setPhone(request.get("phone"));
        }

        if (request.containsKey("department")) {
            faculty.setDepartment(request.get("department"));
        }

        if (request.containsKey("designation")) {
            faculty.setDesignation(request.get("designation"));
        }

        if (request.containsKey("qualification")) {
            faculty.setQualification(request.get("qualification"));
        }

        if (request.containsKey("expierenceYears")) {
            faculty.setExpierenceYears(Integer.parseInt(request.get("expierenceYears")));
        }

        facultyPersonalRepository.save(faculty);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Faculty profile updated successfully");

        return response;
    }
}