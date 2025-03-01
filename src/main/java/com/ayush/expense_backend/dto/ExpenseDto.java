package com.ayush.expense_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    private Long id;
    private Long budgetId;
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDateTime date;
}
