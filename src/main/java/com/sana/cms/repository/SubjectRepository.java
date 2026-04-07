package com.sana.cms.repository;

import com.sana.cms.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsBySubjectCode(String subjectCode);

    List<Subject> findByBranchAndSemester(String branch, int semester);
}