package com.spring.security.service.impl;

import com.spring.security.config.jwt.JwtService;
import com.spring.security.dto.JwtAuthRequest;
import com.spring.security.dto.JwtAuthResponse;
import com.spring.security.dto.UserDto;
import com.spring.security.exception.ResourceNotFoundException;
import com.spring.security.model.Role;
import com.spring.security.model.User;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.service.UserService;
import com.spring.security.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = this.roleRepository.findById(AppConstant.USER_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with Id : " + AppConstant.USER_ID));

        user.setRole(role);

        User savedUser = this.userRepository.save(user);
        String token = this.jwtService.generateToken(user);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public JwtAuthResponse loginUser(JwtAuthRequest request) {
        try {
            this.authenticate(request.getEmail(), request.getPassword());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtService.generateToken(userDetails);
            return JwtAuthResponse.builder().token(token).build();
        } catch (BadCredentialsException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }

    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public UserDto getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userDetailsService.loadUserByUsername(email);
        return this.modelMapper.map(user, UserDto.class);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException exception) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

}
