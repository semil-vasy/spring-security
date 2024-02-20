package com.spring.security.controller;

import com.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Security;

@Controller
public class ViewController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ModelAndView userPage() {
        return getModelWithUserDetails("user");
    }

    @GetMapping("/admin")
    public ModelAndView adminPage() {
        return getModelWithUserDetails("admin");
    }

    private ModelAndView getModelWithUserDetails(String view) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String role = authentication.getAuthorities().stream().map(Object::toString).toList().get(0);
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("email",email);
        modelAndView.addObject("role",role);
        return modelAndView;
    }
}
