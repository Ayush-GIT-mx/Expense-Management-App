package com.ayush.expense_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.UserDto;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.request.AddNewUserRequest;
import com.ayush.expense_backend.request.UpdateUserRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createuser(@RequestBody AddNewUserRequest request) {
        try {
            User user = userService.registerUser(request);
            UserDto userDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse(true, "Sucess", userDto));
        } catch (NoDataFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request,
            @PathVariable("user_id") Long user_id) {
        try {
            User user = userService.updateUser(request, user_id);
            UserDto userDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse(true, "Sucess", userDto));
        } catch (NoDataFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }

    }

}
