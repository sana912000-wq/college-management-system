package com.sana.cms.service;

import com.sana.cms.entity.*;
import com.sana.cms.repository.*;
import com.sana.cms.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyPersonalRepository facultyPersonalRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ========================= LOGIN =========================
    public Map<String, Object> login(String email, String password) {

        Map<String, Object> response = new HashMap<>();

        // STUDENT
        if (studentRepository.findByEmail(email).isPresent()) {
            Student student = studentRepository.findByEmail(email).get();

            if (!passwordEncoder.matches(password, student.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
            }

            response.put("role", "STUDENT");
            response.put("user", student);
            return response;
        }

        // FACULTY
        else if (facultyPersonalRepository.findByEmail(email).isPresent()) {
            FacultyPersonal faculty = facultyPersonalRepository.findByEmail(email).get();

            if (!passwordEncoder.matches(password, faculty.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
            }

            response.put("role", "FACULTY");
            response.put("user", faculty);
            return response;
        }

        // ADMIN
        else if (adminRepository.findByEmail(email).isPresent()) {
            Admin admin = adminRepository.findByEmail(email).get();

            if (!passwordEncoder.matches(password, admin.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
            }

            response.put("role", "ADMIN");
            response.put("user", admin);
            return response;
        }

        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // ========================= FORGOT PASSWORD =========================
    public Map<String, String> forgotPassword(String email) {

        boolean exists =
                studentRepository.findByEmail(email).isPresent() ||
                        facultyPersonalRepository.findByEmail(email).isPresent() ||
                        adminRepository.findByEmail(email).isPresent();

        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not registered");
        }

        // Generate token
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(resetToken);

        // Frontend link
        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        emailService.sendResetEmail(email, resetLink);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Reset link sent to email");

        return response;
    }

    // ========================= RESET PASSWORD =========================
    public Map<String, String> resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token"));

        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");
        }

        String email = resetToken.getEmail();

        // Optional validation
        PasswordValidator.validate(newPassword);

        // STUDENT
        if (studentRepository.findByEmail(email).isPresent()) {
            Student student = studentRepository.findByEmail(email).get();
            student.setPasswordHash(passwordEncoder.encode(newPassword));
            studentRepository.save(student);
        }

        // FACULTY
        else if (facultyPersonalRepository.findByEmail(email).isPresent()) {
            FacultyPersonal faculty = facultyPersonalRepository.findByEmail(email).get();
            faculty.setPasswordHash(passwordEncoder.encode(newPassword));
            facultyPersonalRepository.save(faculty);
        }

        // ADMIN
        else if (adminRepository.findByEmail(email).isPresent()) {
            Admin admin = adminRepository.findByEmail(email).get();
            admin.setPasswordHash(passwordEncoder.encode(newPassword));
            adminRepository.save(admin);
        }

        tokenRepository.delete(resetToken);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset successful");

        return response;
    }

    // ========================= CHANGE PASSWORD =========================
    public Map<String, String> changePassword(String email, String oldPassword, String newPassword) {

        // STUDENT
        if (studentRepository.findByEmail(email).isPresent()) {
            Student student = studentRepository.findByEmail(email).get();

            if (!passwordEncoder.matches(oldPassword, student.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password incorrect");
            }

            PasswordValidator.validate(newPassword);

            student.setPasswordHash(passwordEncoder.encode(newPassword));
            studentRepository.save(student);
        }

        // FACULTY
        else if (facultyPersonalRepository.findByEmail(email).isPresent()) {
            FacultyPersonal faculty = facultyPersonalRepository.findByEmail(email).get();

            if (!passwordEncoder.matches(oldPassword, faculty.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password incorrect");
            }

            PasswordValidator.validate(newPassword);

            faculty.setPasswordHash(passwordEncoder.encode(newPassword));
            facultyPersonalRepository.save(faculty);
        }

        // ADMIN
        else if (adminRepository.findByEmail(email).isPresent()) {
            Admin admin = adminRepository.findByEmail(email).get();

            if (!passwordEncoder.matches(oldPassword, admin.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password incorrect");
            }

            PasswordValidator.validate(newPassword);

            admin.setPasswordHash(passwordEncoder.encode(newPassword));
            adminRepository.save(admin);
        }

        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password changed successfully");

        return response;
    }
}