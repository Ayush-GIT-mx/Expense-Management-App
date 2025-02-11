package com.ayush.expense_backend.service.expense;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayush.expense_backend.dto.ExpenseDto;
import com.ayush.expense_backend.entity.Expense;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.repository.ExpenseRepository;
import com.ayush.expense_backend.repository.UserRepository;
import com.ayush.expense_backend.request.ExpenseRequest;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpenseDto convertToDto(Expense expense) {
        return modelMapper.map(expense, ExpenseDto.class);
    }

    @Override
    public Expense createExpense(ExpenseRequest request, Long user_id) {
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        expense.setDate(LocalDateTime.now());

        User user = userRepository.findById(user_id).orElseThrow(() -> {
            throw new NoDataFoundException("User with id :" + user_id + " not found!");
        });
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    @Override
    public Expense updatExpense(ExpenseRequest request, Long expense_id) {
        return expenseRepository.findById(expense_id).map(expense -> {
            expense.setAmount(request.getAmount());
            expense.setCategory(request.getCategory());
            expense.setDescription(request.getDescription());
            expense.setDate(LocalDateTime.now());
            return expenseRepository.save(expense);
        }).orElseThrow(() -> {
            throw new NoDataFoundException("Expense with id :" + expense_id + " not found!");
        });
    }

    @Override
    public void deleteExpense(Long expense_id) {
        expenseRepository.findById(expense_id).ifPresentOrElse(expenseRepository::delete, () -> {
            throw new NoDataFoundException("Expense with id :" + expense_id + " not found!");
        });
    }

    @Override
    public Expense getExpenseById(Long expense_id) {
        return expenseRepository.findById(expense_id).orElseThrow(() -> {
            throw new NoDataFoundException("Expense with id :" + expense_id + " not found!");
        });
    }

    @Override
    public Expense getexpenseByCategory(String category, Long user_id) {
        return expenseRepository.findByCategoryAndUserId(category, user_id);
    }

    @Override
    public List <Expense> getAllExpenseByUserId(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new NoDataFoundException("User Not Found with id:" + user_id + " ");
        }
        List <Expense> allExpense = expenseRepository.findAllByUserId(user_id);
        return allExpense;
    }


    @Override
    public List<ExpenseDto> getAllDtos(List<Expense> expenses) {
       return expenses.stream().map(this::convertToDto).toList();
    }

}
