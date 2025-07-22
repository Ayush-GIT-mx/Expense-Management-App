package com.ayush.expense_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.UserDto;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.AlreadyExistsException;
import com.ayush.expense_backend.request.AddNewUserRequest;
import com.ayush.expense_backend.request.LoginRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.response.JwtResponse;
import com.ayush.expense_backend.security.jwt.JwtUtils;
import com.ayush.expense_backend.security.user.AppUserDetails;
import com.ayush.expense_backend.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String JwtToken = jwtUtils.generateTokenForUser(authentication);
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), JwtToken);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Login Successfull", jwtResponse),
                    HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Invalid Credentials ," + e.getMessage(), null),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createuser(@RequestBody AddNewUserRequest request) {
        try {
            User user = userService.registerUser(request);
            UserDto userDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse(true, "Sucess", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

}
