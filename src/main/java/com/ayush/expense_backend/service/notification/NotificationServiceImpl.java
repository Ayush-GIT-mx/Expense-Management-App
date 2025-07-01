package com.ayush.expense_backend.service.notification;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayush.expense_backend.dto.NotificationDto;
import com.ayush.expense_backend.entity.Notification;
import com.ayush.expense_backend.entity.User;
import com.ayush.expense_backend.exception.NoDataFoundException;
import com.ayush.expense_backend.repository.NotificationRepository;
import com.ayush.expense_backend.repository.UserRepository;
import com.ayush.expense_backend.request.SendNewNotificationRequest;

@Service

public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public NotificationDto convertToDtoo(Notification notification) {
        return mapper.map(notification, NotificationDto.class);
    }

    @Override
    public List<NotificationDto> getAllDtos(List<Notification> notifications) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDtos'");
    }

    @Override
    public Notification sendNotification(SendNewNotificationRequest request, Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> {
            throw new NoDataFoundException("User Not Found with id:" + user_id + " ");
        });
        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setNotificationType(request.getNotificationtype());
        notification.setTimeStamp(LocalDateTime.now());
        notification.setSeen(request.isSeen());
        notification.setUser(user);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long notification_id) {
        Notification notification = notificationRepository.findById(notification_id).orElseThrow(() -> {
            throw new NoDataFoundException("Notification Not Found with id:" + notification_id + " ");
        });
        return notification;
    }

    @Override
    public List<Notification> getAllNotificationByUserId(Long user_id) {
        List<Notification> notifications = notificationRepository.findAllByUserId(user_id);
        return notifications;
    }

}
