package com.spring.security.service.impl;

import com.spring.security.dto.UserDto;
import com.spring.security.exception.ResourceNotFoundException;
import com.spring.security.model.Role;
import com.spring.security.model.User;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("No role found with ID : " + 1));
        user.setRole(role);
        this.userRepository.save(user);
    }

}
