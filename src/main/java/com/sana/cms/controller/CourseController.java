package com.sana.cms.controller;

import com.sana.cms.dto.CourseRequestDTO;
import com.sana.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // ✅ CREATE COURSE (ADMIN ONLY)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseRequestDTO dto) {
        return ResponseEntity.ok(courseService.createCourse(dto));
    }

    // ✅ GET ALL COURSES
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // ✅ GET COURSE BY ID
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // ✅ UPDATE COURSE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id,
                                          @RequestBody CourseRequestDTO dto) {
        return ResponseEntity.ok(courseService.updateCourse(id, dto));
    }

    // ✅ DELETE COURSE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> getBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(courseService.getBySubject(subjectId));
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<?> getByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(courseService.getByFaculty(facultyId));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam int semester,
                                    @RequestParam String section) {
        return ResponseEntity.ok(courseService.getBySemesterAndSection(semester, section));
    }
}