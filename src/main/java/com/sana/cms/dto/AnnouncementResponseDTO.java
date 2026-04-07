package com.sana.cms.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnnouncementResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String createdBy;
    private String targetAudience;
    private LocalDate expiresAt;
}
