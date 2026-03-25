package com.sana.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String userId;
    private String role;
    private String email;
}
