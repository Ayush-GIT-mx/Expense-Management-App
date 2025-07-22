package com.ayush.expense_backend.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.ReportDto;
import com.ayush.expense_backend.exception.ReportGenerationException;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.service.report.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/reports")
@RequiredArgsConstructor

public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{user_id}")
    public ResponseEntity<ApiResponse> generateReport(@PathVariable Long user_id, @RequestParam Long budget_id,
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        try {
            ReportDto reportDto = reportService.generateReport(user_id, budget_id, startDate, endDate);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Generated Successfully", reportDto),
                    HttpStatus.OK);
        } catch (ReportGenerationException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
