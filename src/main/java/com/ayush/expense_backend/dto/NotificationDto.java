package com.ayush.expense_backend.dto;

import java.time.LocalDateTime;

import com.ayush.expense_backend.enums.NotificationTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long id;
    private String message;
    private NotificationTypes type;
    private LocalDateTime createdAt;
}
