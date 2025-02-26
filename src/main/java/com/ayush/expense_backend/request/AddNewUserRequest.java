package com.ayush.expense_backend.request;

import java.util.List;

import com.ayush.expense_backend.entity.Role;

import lombok.Data;

@Data
public class AddNewUserRequest {
  private String username;
  private String password;
  private String email;
  private List<Role> roles;
  
}
