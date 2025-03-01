package com.ayush.expense_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
