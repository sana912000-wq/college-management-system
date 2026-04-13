package com.sana.cms.service;

import com.sana.cms.dto.CourseRequestDTO;
import com.sana.cms.dto.CourseResponseDTO;
import com.sana.cms.entity.Course;
import com.sana.cms.entity.FacultyPersonal;
import com.sana.cms.entity.Subject;
import com.sana.cms.repository.AttendanceRepository;
import com.sana.cms.repository.CourseRepository;
import com.sana.cms.repository.EnrollmentRepository;
import com.sana.cms.repository.FacultyPersonalRepository;
import com.sana.cms.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final FacultyPersonalRepository facultyRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRepository attendanceRepository;

    public CourseResponseDTO createCourse(CourseRequestDTO dto) {

        FacultyPersonal faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        if (courseRepository.existsByFaculty_IdAndSubject_IdAndSemesterAndSectionAndAcademicYear(
                dto.getFacultyId(),
                dto.getSubjectId(),
                dto.getSemester(),
                dto.getSection(),
                dto.getAcademicYear()
        )) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course already exists");
        }

        Course course = new Course();
        course.setFaculty(faculty);
        course.setSubject(subject);
        course.setSemester(dto.getSemester());
        course.setSection(dto.getSection());
        course.setAcademicYear(dto.getAcademicYear());
        course.setTotalClasses(dto.getTotalClasses());
        course.setCreatedAt(LocalDateTime.now());

        return mapToDTO(courseRepository.save(course));
    }

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        return mapToDTO(course);
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        FacultyPersonal faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        if (!course.getFaculty().getId().equals(dto.getFacultyId()) ||
                !course.getSubject().getId().equals(dto.getSubjectId()) ||
                !course.getSection().equals(dto.getSection()) ||
                !course.getSemester().equals(dto.getSemester()) ||
                !course.getAcademicYear().equals(dto.getAcademicYear())) {

            if (courseRepository.existsByFaculty_IdAndSubject_IdAndSemesterAndSectionAndAcademicYear(
                    dto.getFacultyId(),
                    dto.getSubjectId(),
                    dto.getSemester(),
                    dto.getSection(),
                    dto.getAcademicYear()
            )) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Course already exists");
            }
        }

        course.setFaculty(faculty);
        course.setSubject(subject);
        course.setSemester(dto.getSemester());
        course.setSection(dto.getSection());
        course.setAcademicYear(dto.getAcademicYear());
        course.setTotalClasses(dto.getTotalClasses());

        return mapToDTO(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        // 🔥 validation (VERY IMPORTANT)
        if (enrollmentRepository.existsByCourse_Id(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course is in use (enrollments)");
        }

        if (attendanceRepository.existsByCourse_Id(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course is in use (attendance)");
        }

        courseRepository.delete(course);
    }

    public List<CourseResponseDTO> getBySubject(Long subjectId) {
        return courseRepository.findBySubjectId(subjectId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<CourseResponseDTO> getByFaculty(Long facultyId) {
        return courseRepository.findByFacultyId(facultyId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<CourseResponseDTO> getBySemesterAndSection(int semester, String section) {
        return courseRepository.findBySemesterAndSection(semester, section)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private CourseResponseDTO mapToDTO(Course course) {

        CourseResponseDTO dto = new CourseResponseDTO();

        dto.setId(course.getId());
        dto.setFacultyName(course.getFaculty().getName());
        dto.setSubjectName(course.getSubject().getSubjectName());
        dto.setSemester(course.getSemester());
        dto.setSection(course.getSection());
        dto.setAcademicYear(course.getAcademicYear());
        dto.setTotalClasses(course.getTotalClasses());

        return dto;
    }
}