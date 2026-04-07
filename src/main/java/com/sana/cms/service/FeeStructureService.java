package com.sana.cms.service;

import com.sana.cms.dto.FeeStructureRequestDTO;
import com.sana.cms.dto.FeeStructureResponseDTO;
import com.sana.cms.entity.FeeStructure;
import com.sana.cms.repository.FeeStructureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;

    // ✅ CREATE
    public FeeStructureResponseDTO create(FeeStructureRequestDTO dto) {

        // ❗ prevent duplicate (same branch + semester)
        boolean exists = feeStructureRepository
                .existsByBranchAndSemester(dto.getBranch(), dto.getSemester());

        if (exists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Fee structure already exists for this branch & semester"
            );
        }

        FeeStructure fee = new FeeStructure();

        fee.setBranch(dto.getBranch());
        fee.setSemester(dto.getSemester());
        fee.setTuitionFee(dto.getTuitionFee());
        fee.setHostelFee(dto.getHostelFee());
        fee.setLibraryFee(dto.getLibraryFee());
        fee.setLabFee(dto.getLabFee());

        // 🔥 calculate total automatically
        BigDecimal total = dto.getTuitionFee()
                .add(dto.getHostelFee())
                .add(dto.getLibraryFee())
                .add(dto.getLabFee());

        fee.setTotalFee(total);

        fee.setTotalFee(total);
        fee.setDueDate(dto.getDueDate());
        fee.setCreatedAt(LocalDateTime.now());

        return mapToDTO(feeStructureRepository.save(fee));
    }

    // ✅ GET ALL
    public List<FeeStructureResponseDTO> getAll() {

        return feeStructureRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ GET BY ID
    public FeeStructureResponseDTO getById(Long id) {

        FeeStructure fee = feeStructureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Fee structure not found"));

        return mapToDTO(fee);
    }

    // 🔥 GET BY BRANCH + SEMESTER (IMPORTANT)
    public FeeStructureResponseDTO getByBranchAndSemester(String branch, int semester) {

        FeeStructure fee = feeStructureRepository
                .findByBranchAndSemester(branch, semester)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fee structure not found for given branch & semester"
                ));

        return mapToDTO(fee);
    }

    // ✅ UPDATE
    public FeeStructureResponseDTO update(Long id, FeeStructureRequestDTO dto) {

        FeeStructure fee = feeStructureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Fee structure not found"));

        fee.setBranch(dto.getBranch());
        fee.setSemester(dto.getSemester());
        fee.setTuitionFee(dto.getTuitionFee());
        fee.setHostelFee(dto.getHostelFee());
        fee.setLibraryFee(dto.getLibraryFee());
        fee.setLabFee(dto.getLabFee());

        // 🔥 recalculate total
        BigDecimal total = dto.getTuitionFee()
                .add(dto.getHostelFee())
                .add(dto.getLibraryFee())
                .add(dto.getLabFee());

        fee.setTotalFee(total);

        fee.setTotalFee(total);
        fee.setDueDate(dto.getDueDate());

        return mapToDTO(feeStructureRepository.save(fee));
    }

    // ✅ DELETE
    public void delete(Long id) {

        if (!feeStructureRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Fee structure not found"
            );
        }

        feeStructureRepository.deleteById(id);
    }

    // 🔁 MAPPING
    private FeeStructureResponseDTO mapToDTO(FeeStructure fee) {

        FeeStructureResponseDTO dto = new FeeStructureResponseDTO();

        dto.setId(fee.getId());
        dto.setBranch(fee.getBranch());
        dto.setSemester(fee.getSemester());
        dto.setTuitionFee(fee.getTuitionFee());
        dto.setHostelFee(fee.getHostelFee());
        dto.setLibraryFee(fee.getLibraryFee());
        dto.setLabFee(fee.getLabFee());
        dto.setTotalFee(fee.getTotalFee());
        dto.setDueDate(fee.getDueDate());

        return dto;
    }
}