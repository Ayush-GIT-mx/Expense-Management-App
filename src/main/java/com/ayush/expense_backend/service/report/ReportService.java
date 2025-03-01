package com.ayush.expense_backend.service.report;

import java.time.LocalDate;

import com.ayush.expense_backend.dto.ReportDto;

public interface ReportService {
    ReportDto generateReport(Long user_id, Long budget_id, LocalDate startDate, LocalDate endDate);

    String convertToJson(Object object);
}
