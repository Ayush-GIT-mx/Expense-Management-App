package com.ayush.expense_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUserId(Long user_id);

}
