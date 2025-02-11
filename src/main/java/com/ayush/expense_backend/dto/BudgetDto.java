package com.ayush.expense_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDto {
    private Long id;
    private BigDecimal amount;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
}
