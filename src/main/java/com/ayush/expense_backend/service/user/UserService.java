package com.ayush.expense_backend.service.user;

import com.ayush.expense_backend.dto.UserDto;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.request.AddNewUserRequest;
import com.ayush.expense_backend.request.UpdateUserRequest;

public interface UserService {
    // convert dto to entity
    UserDto convertToUserDto(User user);

    // Register User
    User registerUser(AddNewUserRequest request);

    // Update User
    User updateUser(UpdateUserRequest request , Long user_id);

    // Delete User
    void deleteUser(Long user_id);

    // Get User By Id
    User getUserById(Long user_id);

}
