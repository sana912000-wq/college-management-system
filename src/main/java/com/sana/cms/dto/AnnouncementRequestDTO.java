package com.sana.cms.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementRequestDTO {

    private String title;
    private String description;

    private Long adminId;

    private String targetAudience;

    private LocalDate expiresAt;
}
