package com.ayush.expense_backend.service.category;

import java.util.List;

import com.ayush.expense_backend.dto.CategoryDto;
import com.ayush.expense_backend.entity.Category;
import com.ayush.expense_backend.request.CategoryRequest;

public interface CategoryService {
    CategoryDto convertToDto(Category category);

    Category makeCategory(CategoryRequest request, Long user_id);

    Category updateCategory(Long category_id, CategoryRequest request);

    void deleteCategory(Long category_id);

    List<Category> getAllCategoriesById(Long user_id);

    List<CategoryDto> getAllDtos(List<Category> categories);
}
