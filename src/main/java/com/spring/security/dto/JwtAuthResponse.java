package com.spring.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {

    private String token;
}
