package com.ayush.expense_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.expense_backend.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserId(Long user_id);

}
