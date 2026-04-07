package com.sana.cms.service;

import com.sana.cms.dto.AttendanceRequestDTO;
import com.sana.cms.dto.AttendanceResponseDTO;
import com.sana.cms.entity.Attendance;
import com.sana.cms.entity.Course;
import com.sana.cms.entity.FacultyPersonal;
import com.sana.cms.entity.Student;
import com.sana.cms.repository.AttendanceRepository;
import com.sana.cms.repository.CourseRepository;
import com.sana.cms.repository.FacultyPersonalRepository;
import com.sana.cms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final FacultyPersonalRepository facultyRepository;

    // ✅ MARK ATTENDANCE
    public AttendanceResponseDTO markAttendance(AttendanceRequestDTO dto) {

        // 1. Validate student
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        // 2. Validate course
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        // 3. Get logged-in faculty
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        FacultyPersonal faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));

        // 4. Validate status
        if (!List.of("PRESENT", "ABSENT", "LEAVE").contains(dto.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }

        // 5. Handle date
        LocalDate date = dto.getClassDate() != null ? dto.getClassDate() : LocalDate.now();

        // 6. Prevent duplicate attendance
        if (attendanceRepository.existsByStudentIdAndCourseIdAndClassDate(
                dto.getStudentId(),
                dto.getCourseId(),
                date
        )) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Attendance already marked for this date");
        }

        // 7. Save
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setClassDate(date);
        attendance.setStatus(dto.getStatus());
        attendance.setMarkedBy(faculty);

        Attendance saved = attendanceRepository.save(attendance);

        return mapToResponse(saved);
    }

    // ✅ GET ALL
    public List<AttendanceResponseDTO> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET BY STUDENT
    public List<AttendanceResponseDTO> getByStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET BY COURSE
    public List<AttendanceResponseDTO> getByCourse(Long courseId) {

        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        return attendanceRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ UPDATE ATTENDANCE
    public AttendanceResponseDTO updateAttendance(Long id, AttendanceRequestDTO dto) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance not found"));

        // Only status update allowed
        if (!List.of("PRESENT", "ABSENT", "LEAVE").contains(dto.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }

        attendance.setStatus(dto.getStatus());

        Attendance updated = attendanceRepository.save(attendance);

        return mapToResponse(updated);
    }

    // ✅ DELETE (OPTIONAL)
    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance not found"));

        attendanceRepository.delete(attendance);
    }

    // ✅ RESPONSE MAPPING
    private AttendanceResponseDTO mapToResponse(Attendance a) {
        return new AttendanceResponseDTO(
                a.getId(),
                a.getStudent().getName(),
                a.getCourse().getSection(),
                a.getClassDate(),
                a.getStatus(),
                a.getMarkedBy().getName()
        );
    }
}