package com.sana.cms.service;

import com.sana.cms.dto.JwtResponseDTO;
import com.sana.cms.dto.LoginDTO;
import com.sana.cms.dto.RegisterResponseDTO;
import com.sana.cms.dto.StudentRegisterDTO;
import com.sana.cms.entity.Student;
import com.sana.cms.repository.StudentRepository;
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
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public RegisterResponseDTO register(StudentRegisterDTO dto) {

        PasswordValidator.validate(dto.getPassword());

        if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email already registered"
            );
        }

        try {
            Student student = new Student();

            student.setName(dto.getName());
            student.setEmail(dto.getEmail());
            student.setPhone(dto.getPhone());
            student.setPasswordHash(encoder.encode(dto.getPassword()));
            student.setBranch(dto.getBranch());
            student.setEnrollmentYear(dto.getEnrollmentYear());

            studentRepository.save(student);

            // ✅ DTO instead of Map
            RegisterResponseDTO response = new RegisterResponseDTO();
            response.setId(student.getId());
            response.setName(student.getName());
            response.setEmail(student.getEmail());
            response.setRole("STUDENT");
            response.setMessage("Registration successful. Please login.");

            return response;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong during registration"
            );
        }
    }
    public JwtResponseDTO login(LoginDTO dto) {

        Student student = studentRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (student == null || !encoder.matches(dto.getPassword(), student.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        String token = JwtUtil.generateToken(
                student.getEmail(),
                "STUDENT",
                student.getId()
        );

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setUserId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setRole("STUDENT");

        return response;
    }

    public Map<String, Object> getStudentProfile(String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student not found"
                ));

        Map<String, Object> response = new HashMap<>();

        response.put("name", student.getName());
        response.put("email", student.getEmail());
        response.put("rollNo", student.getRollNo());
        response.put("branch", student.getBranch());
        response.put("semester", student.getEnrollmentYear());
        response.put("role", "STUDENT");

        return response;
    }

    public Map<String, String> updateStudentProfile(String email, Map<String, String> request) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));

        if (request.containsKey("phone")) {
            student.setPhone(request.get("phone"));
        }

        if (request.containsKey("address")) {
            student.setAddress(request.get("address"));
        }

        studentRepository.save(student);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile updated successfully");

        return response;
    }

}