package com.spring.security.controller;

import com.spring.security.dto.UserDto;
import com.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("api/")
public class ViewController {

    @Autowired
    private UserService userService;

    @GetMapping("user")
    public ResponseEntity<UserDto> user() {
        return ResponseEntity.ok(this.userService.getUser());
    }

    @GetMapping("admin")
    public ResponseEntity<List<UserDto>> admin() {
        return ResponseEntity.ok(this.userService.getAllUser());
    }

}
