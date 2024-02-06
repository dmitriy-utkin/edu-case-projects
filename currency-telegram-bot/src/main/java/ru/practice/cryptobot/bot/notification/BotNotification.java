package ru.practice.cryptobot.bot.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.practice.cryptobot.service.NotificationService;

@Service
@RequiredArgsConstructor
public class BotNotification {

    private final AbsSender absSender;

    private final NotificationService notificationService;

    @Scheduled(fixedRate = 120_000)
    public void notification() {
        var notifications = notificationService.getNotificationMessageList();
        notifications.forEach(n -> {
            try {
                absSender.execute(n);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
