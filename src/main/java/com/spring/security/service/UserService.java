package com.spring.security.service;

import com.spring.security.dto.JwtAuthRequest;
import com.spring.security.dto.JwtAuthResponse;
import com.spring.security.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto);
    JwtAuthResponse loginUser(JwtAuthRequest request);
    List<UserDto> getAllUser();
    UserDto getUser();
}
