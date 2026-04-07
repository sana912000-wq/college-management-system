package com.sana.cms.service;

import com.sana.cms.dto.SubjectRequestDTO;
import com.sana.cms.dto.SubjectResponseDTO;
import com.sana.cms.entity.Subject;
import com.sana.cms.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    // 🔥 CREATE SUBJECT
    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {

        // Optional: check duplicate subject_code
        if (subjectRepository.existsBySubjectCode(dto.getSubjectCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Subject code already exists");
        }

        Subject subject = new Subject();
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setSubjectName(dto.getSubjectName());
        subject.setBranch(dto.getBranch());
        subject.setSemester(dto.getSemester());
        subject.setCredits(dto.getCredits());
        subject.setTheoryMarks(dto.getTheoryMarks());
        subject.setPracticalMarks(dto.getPracticalMarks());

        Subject saved = subjectRepository.save(subject);
        return mapToResponse(saved);
    }

    // 🔥 GET ALL SUBJECTS
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 GET SUBJECT BY ID
    public SubjectResponseDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        return mapToResponse(subject);
    }

    // 🔥 UPDATE SUBJECT
    public SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO dto) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        subject.setSubjectCode(dto.getSubjectCode());
        subject.setSubjectName(dto.getSubjectName());
        subject.setBranch(dto.getBranch());
        subject.setSemester(dto.getSemester());
        subject.setCredits(dto.getCredits());
        subject.setTheoryMarks(dto.getTheoryMarks());
        subject.setPracticalMarks(dto.getPracticalMarks());

        Subject updated = subjectRepository.save(subject);
        return mapToResponse(updated);
    }

    // 🔥 DELETE SUBJECT
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        subjectRepository.delete(subject);
    }

    // 🔥 FILTER BY BRANCH + SEMESTER (OPTIONAL BUT STRONG)
    public List<SubjectResponseDTO> getByBranchAndSemester(String branch, int semester) {
        return subjectRepository.findByBranchAndSemester(branch, semester)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 MAPPER METHOD
    private SubjectResponseDTO mapToResponse(Subject subject) {
        SubjectResponseDTO dto = new SubjectResponseDTO();

        dto.setId(subject.getId());
        dto.setSubjectCode(subject.getSubjectCode());
        dto.setSubjectName(subject.getSubjectName());
        dto.setBranch(subject.getBranch());
        dto.setSemester(subject.getSemester());
        dto.setCredits(subject.getCredits());
        dto.setTheoryMarks(subject.getTheoryMarks());
        dto.setPracticalMarks(subject.getPracticalMarks());

        return dto;
    }
}