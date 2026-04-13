package com.sana.cms.service;

import com.sana.cms.dto.MarksRequestDTO;
import com.sana.cms.dto.MarksResponseDTO;
import com.sana.cms.entity.Course;
import com.sana.cms.entity.Marks;
import com.sana.cms.entity.Student;
import com.sana.cms.entity.Subject;
import com.sana.cms.repository.CourseRepository;
import com.sana.cms.repository.MarksRepository;
import com.sana.cms.repository.StudentRepository;
import com.sana.cms.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarksService {

    private final MarksRepository marksRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;

    public MarksResponseDTO createMarks(MarksRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        boolean exists = marksRepository.existsByStudentIdAndSubjectIdAndCourseId(
                dto.getStudentId(), dto.getSubjectId(), dto.getCourseId()
        );

        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Marks already exist for this student");
        }

        int total = dto.getTheoryMarks() + dto.getPracticalMarks();
        String grade = calculateGrade(total);

        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setSubject(subject);
        marks.setCourse(course);
        marks.setTheoryMarks(dto.getTheoryMarks());
        marks.setPracticalMarks(dto.getPracticalMarks());
        marks.setTotalMarks(total);
        marks.setGrade(grade);
        marks.setSemester(dto.getSemester());
        marks.setAcademicYear(dto.getAcademicYear());
        marks.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(marksRepository.save(marks));
    }

    public List<MarksResponseDTO> getAllMarks() {
        return marksRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MarksResponseDTO getMarksById(Long id) {
        Marks marks = marksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marks not found"));

        return mapToDTO(marks);
    }

    public List<MarksResponseDTO> getMarksByStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"
                ));

        List<Marks> marksList = marksRepository.findByStudentId(studentId);

        return marksList.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<MarksResponseDTO> getMarksByCourse(Long courseId) {
        return marksRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MarksResponseDTO updateMarks(Long id, MarksRequestDTO dto) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marks not found"));

        int total = dto.getTheoryMarks() + dto.getPracticalMarks();
        String grade = calculateGrade(total);

        marks.setTheoryMarks(dto.getTheoryMarks());
        marks.setPracticalMarks(dto.getPracticalMarks());
        marks.setTotalMarks(total);
        marks.setGrade(grade);
        marks.setSemester(dto.getSemester());
        marks.setAcademicYear(dto.getAcademicYear());
        marks.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(marksRepository.save(marks));
    }

    public void deleteMarks(Long id) {
        if (!marksRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marks not found");
        }
        marksRepository.deleteById(id);
    }

    private String calculateGrade(int total) {
        if (total >= 90) return "A+";
        else if (total >= 80) return "A";
        else if (total >= 70) return "B+";
        else if (total >= 60) return "B";
        else if (total >= 50) return "C";
        else if (total >= 40) return "D";
        else return "F";
    }

    private MarksResponseDTO mapToDTO(Marks marks) {
        MarksResponseDTO dto = new MarksResponseDTO();
        dto.setId(marks.getId());
        dto.setStudentId(marks.getStudent().getId());
        dto.setSubjectId(marks.getSubject().getId());
        dto.setCourseId(marks.getCourse().getId());
        dto.setTheoryMarks(marks.getTheoryMarks());
        dto.setPracticalMarks(marks.getPracticalMarks());
        dto.setTotalMarks(marks.getTotalMarks());
        dto.setGrade(marks.getGrade());
        dto.setSemester(marks.getSemester());
        dto.setAcademicYear(marks.getAcademicYear());
        return dto;
    }
}