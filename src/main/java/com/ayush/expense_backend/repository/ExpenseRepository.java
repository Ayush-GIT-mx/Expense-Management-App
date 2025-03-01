package com.ayush.expense_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByCategoryAndUserId(String category, Long user_id);

    Expense findByUserId(Long user_id);

    List<Expense> findAllByUserId(Long user_id);

    List<Expense> findByBudgetId(Long budget_id);

}
