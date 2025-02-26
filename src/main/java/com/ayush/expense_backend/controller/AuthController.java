package com.ayush.expense_backend.controller;

import org.springframework.security.core.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.request.LoginRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.response.JwtResponse;
import com.ayush.expense_backend.security.jwt.JwtUtils;
import com.ayush.expense_backend.security.user.AppUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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
}
