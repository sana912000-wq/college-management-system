package com.sana.cms.repository;

import com.sana.cms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

        // already existing
        boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

        // ✅ REQUIRED (for service)
        List<Enrollment> findByStudentId(Long studentId);

        List<Enrollment> findByCourse_Id(Long courseId);

        // ✅ used for validations
        boolean existsByCourse_Id(Long courseId);

        boolean existsBySubject_Id(Long subjectId);
}