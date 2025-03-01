package com.ayush.expense_backend.service.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ayush.expense_backend.dto.BudgetDto;
import com.ayush.expense_backend.dto.ExpenseDto;
import com.ayush.expense_backend.dto.ReportDto;
import com.ayush.expense_backend.entity.Budget;
import com.ayush.expense_backend.entity.Expense;
import com.ayush.expense_backend.entity.Report;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.repository.BudgetRepository;
import com.ayush.expense_backend.repository.ExpenseRepository;
import com.ayush.expense_backend.repository.ReportRepository;
import com.ayush.expense_backend.repository.UserRepository;
import com.ayush.expense_backend.service.budget.BudgetService;
import com.ayush.expense_backend.service.expense.ExpenseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
        private final ReportRepository reportRepository;
        private final UserRepository userRepository;
        private final ExpenseRepository expenseRepository;
        private final BudgetRepository budgetRepository;
        private final ExpenseService expenseService;
        private final BudgetService budgetService;

        private final ObjectMapper objectMapper;

        @Override
        public ReportDto generateReport(Long user_id, Long budget_id, LocalDate startDate, LocalDate endDate) {
                User user = userRepository.findById(user_id)
                                .orElseThrow(() -> new NoDataFoundException("User Not Found with id:" + user_id));
                Budget budget = budgetRepository.findById(budget_id)
                                .orElseThrow(() -> new NoDataFoundException("Budget Not Found with id:" + budget_id));
                BudgetDto budgetDto = budgetService.convertTDto(budget);

                List<Expense> expenses = expenseRepository.findByBudgetId(budget_id);
                List<ExpenseDto> expenseDtos = expenseService.getAllDtos(expenses);

                BigDecimal totalExpense = expenses.stream()
                                .map(Expense::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                Map<String, BigDecimal> categoryWiseExpense = expenses.stream()
                                .collect(Collectors.groupingBy(Expense::getCategory,
                                                Collectors.reducing(BigDecimal.ZERO, Expense::getAmount,
                                                                BigDecimal::add)));
                categoryWiseExpense = Map.copyOf(categoryWiseExpense);
                String categoryWiseExpenses = convertToJson(categoryWiseExpense);

                Map<LocalDate, BigDecimal> dailyExpenseMap = expenses.stream()
                                .collect(Collectors.groupingBy(expense -> expense.getDate().toLocalDate(),
                                                Collectors.reducing(BigDecimal.ZERO, Expense::getAmount,
                                                                BigDecimal::add)));
                dailyExpenseMap = Map.copyOf(dailyExpenseMap);
                String dailyExpenses = convertToJson(dailyExpenseMap);

                Report report = new Report();
                report.setCreatedAt(LocalDateTime.now());
                report.setTotalExpense(totalExpense);
                report.setStartDate(startDate);
                report.setEndDate(endDate);
                report.setUser(user);
                Report savedReport = reportRepository.save(report);

                ReportDto reportDto = new ReportDto();
                reportDto.setId(savedReport.getId());
                reportDto.setCreatedAt(savedReport.getCreatedAt());
                reportDto.setStartDate(savedReport.getStartDate());
                reportDto.setEndDate(savedReport.getEndDate());
                reportDto.setTotalExpense(totalExpense);
                reportDto.setBudget_id(budget_id);
                reportDto.setUser_id(user_id);
                reportDto.setCategoryWiseExpenses(categoryWiseExpenses);
                reportDto.setDailyExpenses(dailyExpenses);
                reportDto.setExpenses(expenseDtos);
                reportDto.setBudget(budgetDto);
                return reportDto;
        }

        @Override
        public String convertToJson(Object object) {
                try {
                        return objectMapper.writeValueAsString(object);
                } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error converting to JSON", e);
                }
        }
}
