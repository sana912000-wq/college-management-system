package com.sana.cms.repository;

import com.sana.cms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

        List<Course> findBySubjectId(Long subjectId);

        List<Course> findByFacultyId(Long facultyId);

        List<Course> findBySemesterAndSection(int semester, String section);

    boolean existsByFaculty_IdAndSubject_IdAndSemesterAndSectionAndAcademicYear(
            Long facultyId,
            Long subjectId,
            Integer semester,
            String section,
            String academicYear
    );
}
