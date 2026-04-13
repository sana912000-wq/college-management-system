package com.sana.cms.repository;

import com.sana.cms.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    List<Marks> findByStudentId(Long studentId);

    List<Marks> findByCourseId(Long courseId);

    boolean existsByStudentIdAndSubjectIdAndCourseId(
            Long studentId,
            Long subjectId,
            Long courseId
    );
}
