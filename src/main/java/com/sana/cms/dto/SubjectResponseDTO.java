package com.sana.cms.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectResponseDTO {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private String branch;
    private Integer semester;
    private Integer credits;
    private Integer theoryMarks;
    private Integer practicalMarks;
}


