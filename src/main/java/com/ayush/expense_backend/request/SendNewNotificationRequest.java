package com.ayush.expense_backend.request;

import lombok.Data;

@Data
public class SendNewNotificationRequest {
    private String message;
    private String notificationtype;
    private boolean seen;
}
