package com.ayush.expense_backend.service.category;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ayush.expense_backend.dto.CategoryDto;
import com.ayush.expense_backend.entity.Category;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.repository.CategoryRepository;
import com.ayush.expense_backend.repository.UserRepository;
import com.ayush.expense_backend.request.CategoryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceimpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public CategoryDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Category makeCategory(CategoryRequest request, Long user_id) {
        Category category = new Category();
        category.setName(request.getName());
        // mapping user
        User user = userRepository.findById(user_id).orElseThrow(() -> {
            throw new NoDataFoundException("User Not Found with id:" + user_id + " ");
        });
        category.setUser(user);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long category_id, CategoryRequest request) {
        Category existingCategory = categoryRepository.findById(category_id).orElseThrow(() -> {
            throw new NoDataFoundException("Category Not Found with id:" + category_id + " ");
        });
        existingCategory.setName(request.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long category_id) {
        categoryRepository.findById(category_id).ifPresentOrElse(categoryRepository::delete, () -> {
            throw new NoDataFoundException("Category with id :" + category_id + " not found!");
        });
    }

    @Override
    public List<Category> getAllCategoriesById(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new NoDataFoundException("User Not Found with id:" + user_id + " ");
        }
        List<Category> categories = categoryRepository.findAllByUserId(user_id);
        return categories;
    }

    @Override
    public List<CategoryDto> getAllDtos(List<Category> categories) {
        return categories.stream().map(this::convertToDto).toList();
    }

}
