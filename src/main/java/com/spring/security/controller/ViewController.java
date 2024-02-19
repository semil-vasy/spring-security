package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController
{
    @GetMapping("/user")
    public ModelAndView userPage(){
        return new ModelAndView("user");
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(){
        return new ModelAndView("admin");
    }
}
