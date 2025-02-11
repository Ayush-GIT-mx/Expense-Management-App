package com.ayush.expense_backend.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BudgetRequest {
    private BigDecimal amount;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
}
