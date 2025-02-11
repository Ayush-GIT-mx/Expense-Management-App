package com.ayush.expense_backend.service.budget;

import java.util.List;
import java.util.Optional;

import com.ayush.expense_backend.dto.BudgetDto;
import com.ayush.expense_backend.entity.Budget;
import com.ayush.expense_backend.request.BudgetRequest;
import com.ayush.expense_backend.request.UpdateBudgetRequest;

public interface BudgetService {
    BudgetDto convertTDto(Budget budget);

    Budget makeBudget(BudgetRequest request, Long user_id);

    Budget updateBudget(UpdateBudgetRequest request, Long budget_id);

    void deleteBudget(Long budget_id);

    Optional<Budget> getallBudgetbyUserId(Long user_id);

    List<BudgetDto> getallDtos(Optional<Budget> budgets);

}
