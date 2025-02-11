package com.ayush.expense_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findAllByUserId(Long user_id);

}
