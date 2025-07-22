package com.ayush.expense_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.expense_backend.dto.NotificationDto;
import com.ayush.expense_backend.entity.Notification;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.request.SendNewNotificationRequest;
import com.ayush.expense_backend.response.ApiResponse;
import com.ayush.expense_backend.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/notification")
@RequiredArgsConstructor

public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/{user_id}")
    public ResponseEntity<ApiResponse> sendNotification(@RequestBody SendNewNotificationRequest request,
            @PathVariable Long user_id) {
        try {
            Notification notification = notificationService.sendNotification(request, user_id);
            NotificationDto notificationDto = notificationService.convertToDtoo(notification);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sent Successfully", notificationDto),
                    HttpStatus.OK);
        } catch (NoDataFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
