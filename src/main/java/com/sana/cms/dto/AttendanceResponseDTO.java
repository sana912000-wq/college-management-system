package com.sana.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AttendanceResponseDTO {

    private Long id;
    private String studentName;
    private String courseName;
    private LocalDate classDate;
    private String status;
    private String markedBy;
}
