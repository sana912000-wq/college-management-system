package com.sana.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDTO {
    private Long id;
    private String studentName;
    private String subjectName;
    private String courseDetails;
    private Integer semester;
    private String academicYear;
    private String status;
}
