package ru.practice.cryptobot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.practice.cryptobot.dao.Subscription;
import ru.practice.cryptobot.dto.NotificationType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    default Subscription telegramUserToSubscription(Long chatId, User user) {
        return Subscription.builder()
                .userName(user.getUserName())
                .userId(user.getId())
                .chatId(chatId)
                .build();
    }

    default Subscription telegramUserToSubscription(NotificationType type, Long chatId, User user) {
        return Subscription.builder()
                .userName(user.getUserName())
                .userId(user.getId())
                .chatId(chatId)
                .notificationType(type)
                .build();
    }

}
