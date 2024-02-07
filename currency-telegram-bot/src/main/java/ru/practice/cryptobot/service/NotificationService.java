package ru.practice.cryptobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.practice.cryptobot.dao.Subscription;
import ru.practice.cryptobot.dto.Notification;
import ru.practice.cryptobot.dto.NotificationType;
import ru.practice.cryptobot.mapper.NotificationMapper;
import ru.practice.cryptobot.repository.NotificationRepository;
import ru.practice.cryptobot.repository.SubscribeRepository;
import ru.practice.cryptobot.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final SubscribeRepository subscribeRepository;

    private final CryptoCurrencyService cryptoCurrencyService;

    private final NotificationMapper notificationMapper;

    public List<SendMessage> getNotificationMessageList(){
        var notificationList = getNotificationList();
        return notificationList.stream()
                .map(this::getNotificationMessage)
                .toList();
    }

    private SendMessage getNotificationMessage(Notification notification) {
        String messageTemplate = "Time to " + notification.getNotificationType().toString() +
                "! BTC price is " + TextUtil.toString(cryptoCurrencyService.getBitcoinPrice()) + " USD";

        SendMessage message = new SendMessage();

        message.setChatId(notification.getChatId());
        message.setText(messageTemplate);
        return message;
    }


    private List<Notification> getNotificationList() {
        List<Notification> notifications = new ArrayList<>();

        var currentBtcPrice = cryptoCurrencyService.getBitcoinPrice();
        var lessPriceSubscription = subscribeRepository.findByNotificationTypeAndPriceLessThan(NotificationType.SELL, currentBtcPrice);
        var greaterPriceSubscription = subscribeRepository.findByNotificationTypeAndPriceGreaterThan(NotificationType.BUY, currentBtcPrice);

        notifications.addAll(getPreparedNotification(lessPriceSubscription));
        notifications.addAll(getPreparedNotification(greaterPriceSubscription));

        return notifications;
    }

    private List<Notification> getPreparedNotification(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .filter(subscription -> !notificationRepository.userIsNotified(subscription.getUserName(), subscription.getNotificationType()))
                .map(subscription -> {
                    notificationRepository.updateNotificationList(subscription.getUserName(), subscription.getNotificationType());
                    return notificationMapper.subscriptionToNotification(subscription);
                })
                .collect(Collectors.toList());
    }


}
