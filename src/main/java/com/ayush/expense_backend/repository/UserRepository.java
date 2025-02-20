package com.ayush.expense_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
