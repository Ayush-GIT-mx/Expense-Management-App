package com.ayush.expense_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ReportDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal TotalExpense;
    private Long budget_id;
    private Long user_id;
    private String categoryWiseExpenses;
    private String dailyExpenses;
    private List<ExpenseDto> expenses;
    private BudgetDto budget;

}
