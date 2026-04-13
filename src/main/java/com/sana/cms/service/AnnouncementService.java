package com.sana.cms.service;

import com.sana.cms.dto.AnnouncementRequestDTO;
import com.sana.cms.dto.AnnouncementResponseDTO;
import com.sana.cms.entity.Admin;
import com.sana.cms.entity.Announcement;
import com.sana.cms.repository.AdminRepository;
import com.sana.cms.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AdminRepository adminRepository;

    public AnnouncementResponseDTO create(AnnouncementRequestDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Admin not found"));

        Announcement a = new Announcement();

        a.setTitle(dto.getTitle());
        a.setDescription(dto.getDescription());
        a.setCreatedBy(admin);
        a.setTargetAudience(dto.getTargetAudience());
        a.setCreatedAt(LocalDateTime.now());
        a.setExpiresAt(dto.getExpiresAt());

        return mapToDTO(announcementRepository.save(a));
    }

    public List<AnnouncementResponseDTO> getAll() {
        return announcementRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public AnnouncementResponseDTO getById(Long id) {
        Announcement a = announcementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Announcement not found"));

        return mapToDTO(a);
    }

    public List<AnnouncementResponseDTO> getActive() {

        LocalDate today = LocalDate.now();

        return announcementRepository.findAll()
                .stream()
                .filter(a ->
                        a.getExpiresAt() == null ||
                                !a.getExpiresAt().isBefore(today))
                .map(this::mapToDTO)
                .toList();
    }

    public AnnouncementResponseDTO update(Long id, AnnouncementRequestDTO dto) {

        Announcement a = announcementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Announcement not found"));

        a.setTitle(dto.getTitle());
        a.setDescription(dto.getDescription());
        a.setTargetAudience(dto.getTargetAudience());
        a.setExpiresAt(dto.getExpiresAt());

        return mapToDTO(announcementRepository.save(a));
    }

    public void delete(Long id) {

        if (!announcementRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Announcement not found");
        }

        announcementRepository.deleteById(id);
    }

    public List<AnnouncementResponseDTO> getByTarget(String target) {
        return announcementRepository.findByTargetAudience(target)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private AnnouncementResponseDTO mapToDTO(Announcement a) {

        AnnouncementResponseDTO dto = new AnnouncementResponseDTO();

        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setDescription(a.getDescription());
        dto.setCreatedBy(a.getCreatedBy().getName());
        dto.setTargetAudience(a.getTargetAudience());
        dto.setExpiresAt(a.getExpiresAt());

        return dto;
    }
}