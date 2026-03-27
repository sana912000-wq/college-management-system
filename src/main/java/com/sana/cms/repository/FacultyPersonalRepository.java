package com.sana.cms.repository;

import com.sana.cms.entity.FacultyPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FacultyPersonalRepository extends JpaRepository<FacultyPersonal, Long>{
    Optional<FacultyPersonal> findByEmail(String email);
}
