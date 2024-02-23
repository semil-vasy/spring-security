package com.spring.security.controller;

import com.spring.security.config.jwt.JwtService;
import com.spring.security.dto.JwtAuthRequest;
import com.spring.security.dto.JwtAuthResponse;
import com.spring.security.dto.UserDto;
import com.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {
        JwtAuthResponse jwtAuthResponse = this.userService.loginUser(request);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }


}
