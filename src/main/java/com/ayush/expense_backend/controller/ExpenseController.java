package com.ayush.expense_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.ExpenseDto;
import com.ayush.expense_backend.entity.Expense;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.exception.NoOperationPerformedException;
import com.ayush.expense_backend.request.ExpenseRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.service.expense.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/{user_id}")
    public ResponseEntity<ApiResponse> createExpense(@RequestBody ExpenseRequest request,
            @PathVariable("user_id") Long user_id) {
        try {
            Expense expense = expenseService.createExpense(request, user_id);
            ExpenseDto expenseDto = expenseService.convertToDto(expense);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sucess", expenseDto), HttpStatus.CREATED);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{expense_id}")
    public ResponseEntity<ApiResponse> updateExpense(@RequestBody ExpenseRequest request,
            @PathVariable("expense_id") Long expense_id) {
        try {
            Expense expense = expenseService.updatExpense(request, expense_id);
            ExpenseDto expenseDto = expenseService.convertToDto(expense);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Expense Update Sucess", expenseDto),
                    HttpStatus.OK);
        } catch (NoOperationPerformedException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), expense_id),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{expense_id}")
    public ResponseEntity<ApiResponse> deleteExpense(@PathVariable("expense_id") Long expense_id) {
        try {
            expenseService.deleteExpense(expense_id);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Expense Deleted successfully", null),
                    HttpStatus.OK);
        } catch (NoOperationPerformedException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/category/{user_id}") // fetch through category
    public ResponseEntity<ApiResponse> getExpenseByCategory(@RequestParam Long user_id, @RequestParam String category) {
        try {
            Expense expense = expenseService.getexpenseByCategory(category, user_id);
            ExpenseDto expenseDto = expenseService.convertToDto(expense);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Fetched Successfully", expenseDto),
                    HttpStatus.FOUND);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllExpenseByUserId(@RequestParam Long user_id) {
        try {
            List<Expense> expenses = expenseService.getAllExpenseByUserId(user_id);
            List<ExpenseDto> expenseDtos = expenseService.getAllDtos(expenses);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Fetched Successfully", expenseDtos),
                    HttpStatus.FOUND);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{expense_id}")
    public ResponseEntity<ApiResponse> getExpenseByid(@PathVariable Long expense_id){
        try {
            Expense expense = expenseService.getExpenseById(expense_id);
            ExpenseDto expenseDto = expenseService.convertToDto(expense);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Fetched Successfully", expenseDto), HttpStatus.OK);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error : No Expense Found With id:" + expense_id +" ", null), HttpStatus.NOT_FOUND);
        }
    }

}
