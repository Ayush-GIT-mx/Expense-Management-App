package com.ayush.expense_backend.service.notification;

import java.util.List;

import com.ayush.expense_backend.dto.NotificationDto;
import com.ayush.expense_backend.entity.Notification;
import com.ayush.expense_backend.request.SendNewNotificationRequest;

public interface NotificationService {
    NotificationDto convertToDtoo(Notification notification);

    List<NotificationDto> getAllDtos(List<Notification> notifications);

    Notification sendNotification(SendNewNotificationRequest request , Long user_id);

    Notification getNotificationById(Long notification_id);

    List<Notification> getAllNotificationByUserId(Long user_id);

}
