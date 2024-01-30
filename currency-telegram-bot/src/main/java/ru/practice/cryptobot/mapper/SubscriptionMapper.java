package ru.practice.cryptobot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.practice.cryptobot.dao.Subscription;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    default Subscription telegramUserToSubscription(User user) {
        return Subscription.builder()
                .userName(user.getUserName())
                .userId(user.getId())
                .build();
    }

}
