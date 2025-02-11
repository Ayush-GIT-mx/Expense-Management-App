package com.ayush.expense_backend.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String password;
    private String email;
}
