package com.spring.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthRequest {
    private String email;
    private String password;
}
