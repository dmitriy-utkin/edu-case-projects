package ru.practice.cryptobot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practice.cryptobot.dao.Subscription;
import ru.practice.cryptobot.dto.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    default Notification subscriptionToNotification(Subscription subscription) {
        return Notification.builder()
                .notificationType(subscription.getNotificationType())
                .chatId(subscription.getChatId())
                .userName(subscription.getUserName())
                .build();
    };
}
