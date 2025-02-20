package com.ayush.expense_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.BudgetDto;
import com.ayush.expense_backend.entity.Budget;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.exception.NoOperationPerformedException;
import com.ayush.expense_backend.request.BudgetRequest;
import com.ayush.expense_backend.request.UpdateBudgetRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.service.budget.BudgetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping("/{user_id}")
    public ResponseEntity<ApiResponse> createBudget(@RequestBody BudgetRequest request,
            @PathVariable("user_id") Long user_id) {
        try {
            Budget budget = budgetService.makeBudget(request, user_id);
            BudgetDto budgetDto = budgetService.convertTDto(budget);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Budget Created", budgetDto), HttpStatus.OK);
        } catch (NoOperationPerformedException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error : Unable to Make a Budget", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{budget_id}")
    public ResponseEntity<ApiResponse> updateBudget(@RequestBody UpdateBudgetRequest request,
            @PathVariable("budget_id") Long budget_id) {
        try {
            Budget budget = budgetService.updateBudget(null, budget_id);
            BudgetDto budgetDto = budgetService.convertTDto(budget);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Budget Updated", budgetDto), HttpStatus.OK);
        } catch (NoOperationPerformedException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error : Unable to Update a Budget", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{budget_id}")
    public ResponseEntity<ApiResponse> deleteBudget(@PathVariable("budget_id") Long budget_id) {
        try {
            budgetService.deleteBudget(budget_id);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Budget Deleted", null), HttpStatus.OK);
        } catch (NoOperationPerformedException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error : Unable to Delete a Budget", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ApiResponse> getAllBudgetByUserId(@PathVariable Long user_id) {
        try {
            Optional<Budget> budgets = budgetService.getallBudgetbyUserId(user_id);
            List<BudgetDto> budgetDtos = budgetService.getallDtos(budgets);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Budgets Fetched", budgetDtos), HttpStatus.OK);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error : Unable to Fetch Budgets | No user Found with id:" + user_id + "",
                            null),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
