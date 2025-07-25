package com.ayush.expense_backend.service.expense;

import java.util.List;

import com.ayush.expense_backend.dto.ExpenseDto;
import com.ayush.expense_backend.entity.Expense;
import com.ayush.expense_backend.request.ExpenseRequest;

public interface ExpenseService {
    ExpenseDto convertToDto(Expense expense);

    Expense createExpense(ExpenseRequest request, Long user_id, Long budget_id);

    Expense updatExpense(ExpenseRequest request, Long expense_id);

    void deleteExpense(Long expense_id);

    Expense getExpenseById(Long expense_id);

    Expense getexpenseByCategory(String category, Long user_id);

    List<Expense> getAllExpenseByUserId(Long user_id);

    List<Expense> getAllExpenseByBudgetId(Long budgetId);

    List<ExpenseDto> getAllDtos(List<Expense> expenses);

}
