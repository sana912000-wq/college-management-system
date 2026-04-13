package com.sana.cms.repository;

import com.sana.cms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByCourseId(Long courseId);
    boolean existsByStudentIdAndCourseIdAndClassDate(
            Long studentId,
            Long courseId,
            LocalDate classDate
    );
    boolean existsByCourse_Id(Long courseId);
}