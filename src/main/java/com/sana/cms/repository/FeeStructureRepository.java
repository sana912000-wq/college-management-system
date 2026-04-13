package com.sana.cms.repository;

import com.sana.cms.entity.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {

    Optional<FeeStructure> findByBranchAndSemester(String branch, int semester);

    boolean existsByBranchAndSemester(String branch, int semester);
}