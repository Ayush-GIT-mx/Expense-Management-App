package com.ayush.expense_backend.service.budget;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ayush.expense_backend.dto.BudgetDto;
import com.ayush.expense_backend.entity.Budget;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.exception.NoOperationPerformedException;
import com.ayush.expense_backend.repository.BudgetRepository;
import com.ayush.expense_backend.repository.UserRepository;
import com.ayush.expense_backend.request.BudgetRequest;
import com.ayush.expense_backend.request.UpdateBudgetRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public BudgetDto convertTDto(Budget budget) {
        return modelMapper.map(budget, BudgetDto.class);
    }

    @Override
    public Budget makeBudget(BudgetRequest request, Long user_id) {
        Budget budget = new Budget();
        budget.setAmount(request.getAmount());
        budget.setCategory(request.getCategory());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        // mapping user to budget Object
        User user = userRepository.findById(user_id).orElseThrow(() -> {
            throw new NoDataFoundException("User Not Found with id:" + user_id + " ");
        });
        budget.setUser(user);
        return budgetRepository.save(budget);

    }

    @Override
    public Budget updateBudget(UpdateBudgetRequest request, Long budget_id) {
        Budget exitsingBudget = budgetRepository.findById(budget_id).orElseThrow(() -> {
            throw new NoDataFoundException("No Budget Found within the DataBase");
        });

        exitsingBudget.setAmount(request.getAmount());
        exitsingBudget.setCategory(request.getCategory());
        return budgetRepository.save(exitsingBudget);
    }

    @Override
    public void deleteBudget(Long budget_id) {
        budgetRepository.findById(budget_id).ifPresentOrElse(budgetRepository::delete, () -> {
            throw new NoOperationPerformedException("Error : No Operation Was Performed");
        });
    }

    @Override
    public Optional<Budget> getallBudgetbyUserId(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new NoDataFoundException("User Not Found with id:" + user_id + " ");
        }
        return budgetRepository.findAllByUserId(user_id);
    }

    @Override
    public List<BudgetDto> getallDtos(Optional<Budget> budgets) {
        return budgets.stream().map(this::convertTDto).toList();
    }

}
