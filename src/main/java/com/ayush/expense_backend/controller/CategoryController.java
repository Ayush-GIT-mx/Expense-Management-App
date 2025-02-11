package com.ayush.expense_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.CategoryDto;
import com.ayush.expense_backend.entity.Category;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.request.CategoryRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{user_id}/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryRequest request,
            @PathVariable("user_id") Long user_id) {
        try {
            Category category = categoryService.makeCategory(request, user_id);
            CategoryDto categoryDto = categoryService.convertToDto(category);
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(true, " Custom Category Created Succesfully", categoryDto), HttpStatus.CREATED);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{user_id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("user_id") Long user_id , @RequestBody CategoryRequest request){
        try {
            Category category = categoryService.updateCategory(user_id, request);
            CategoryDto categoryDto = categoryService.convertToDto(category);
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(true, " Custom Category Updated Succesfully", categoryDto), HttpStatus.OK);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, e.getMessage(), null), HttpStatus.BAD_REQUEST
            );
        }
    }
}
