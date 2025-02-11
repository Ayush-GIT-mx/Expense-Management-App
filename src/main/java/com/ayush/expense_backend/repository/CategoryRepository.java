package com.ayush.expense_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId(Long user_id);

}
