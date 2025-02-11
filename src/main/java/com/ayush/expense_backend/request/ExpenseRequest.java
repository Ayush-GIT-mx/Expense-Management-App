package com.ayush.expense_backend.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExpenseRequest {
    private BigDecimal amount;
    private String category;
    private String description;
}
