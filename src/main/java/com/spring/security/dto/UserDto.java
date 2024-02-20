package com.spring.security.dto;

import com.spring.security.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private long id;

    private String name;

    private String email;

    private String password;

    private Role role;
}
