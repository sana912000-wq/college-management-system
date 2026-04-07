package com.sana.cms.service;

import com.sana.cms.dto.EnrollmentRequestDTO;
import com.sana.cms.dto.EnrollmentResponseDTO;
import com.sana.cms.entity.Course;
import com.sana.cms.entity.Enrollment;
import com.sana.cms.entity.Student;
import com.sana.cms.repository.CourseRepository;
import com.sana.cms.repository.EnrollmentRepository;
import com.sana.cms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // ✅ Allowed status values
    private static final Set<String> VALID_STATUS = Set.of("ACTIVE", "DROPPED", "COMPLETED");

    // ✅ ENROLL STUDENT
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        // 🔥 Duplicate check
        if (enrollmentRepository.existsByStudentIdAndCourseId(dto.getStudentId(), dto.getCourseId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student already enrolled");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSubject(course.getSubject()); // correct approach
        enrollment.setSemester(course.getSemester());
        enrollment.setAcademicYear(course.getAcademicYear());
        enrollment.setStatus("ACTIVE");

        return mapToResponse(enrollmentRepository.save(enrollment));
    }

    // ✅ GET ALL
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EnrollmentResponseDTO getById(Long id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        return mapToResponse(e);
    }

    // ✅ GET BY STUDENT
    public List<EnrollmentResponseDTO> getByStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET BY COURSE
    public List<EnrollmentResponseDTO> getByCourse(Long courseId) {

        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        return enrollmentRepository.findByCourse_Id(courseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ UPDATE STATUS (IMPROVED 🔥)
    public EnrollmentResponseDTO updateStatus(Long id, String status) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        // 🔥 Validation
        if (!VALID_STATUS.contains(status.toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: ACTIVE, DROPPED, COMPLETED");
        }

        enrollment.setStatus(status.toUpperCase());

        return mapToResponse(enrollmentRepository.save(enrollment));
    }

    // ✅ DELETE (SAFE VERSION 🔥)
    public void deleteEnrollment(Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        // 🔥 Best practice: don't delete ACTIVE enrollments
        if ("ACTIVE".equals(enrollment.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot delete ACTIVE enrollment. Drop it first.");
        }

        enrollmentRepository.delete(enrollment);
    }

    // ✅ MAPPER
    private EnrollmentResponseDTO mapToResponse(Enrollment e) {
        return new EnrollmentResponseDTO(
                e.getId(),
                e.getStudent().getName(),
                e.getSubject().getSubjectName(),
                e.getCourse().getSection(),
                e.getSemester(),
                e.getAcademicYear(),
                e.getStatus()
        );
    }
}