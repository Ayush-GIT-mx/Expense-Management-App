package com.ayush.expense_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
