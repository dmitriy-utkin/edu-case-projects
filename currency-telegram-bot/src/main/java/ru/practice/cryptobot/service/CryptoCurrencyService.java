package ru.practice.cryptobot.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.practice.cryptobot.client.BinanceClient;
import ru.practice.cryptobot.dao.Subscription;
import ru.practice.cryptobot.dto.NotificationType;
import ru.practice.cryptobot.mapper.SubscriptionMapper;
import ru.practice.cryptobot.repository.SubscribeRepository;
import ru.practice.cryptobot.utils.TextUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyService {

    private final AtomicReference<Double> price = new AtomicReference<>();

    private final BinanceClient client;

    private final SubscribeRepository subscribeRepository;

    private final SubscriptionMapper subscriptionMapper;

    @SneakyThrows
    public double getBitcoinPrice() {
        if (price.get() == null) {
            price.set(client.getBitcoinPrice());
        }
        return price.get();
    }

    public void saveNewUser(Long chatId, User user) {
        if (subscribeRepository.existsByUserId(user.getId())) {
            return;
        }
        subscribeRepository.save(subscriptionMapper.telegramUserToSubscription(chatId, user));
    }

    public String subscribe(Long chatId, User user, String text) {

        NotificationType type = null;

        if (text.toUpperCase().contains("BUY")) {
            type = NotificationType.BUY;
        }
        if (text.toUpperCase().contains("SELL")) {
            type = NotificationType.SELL;
        }

        text = preparePriceString(text);

        if (!text.matches("[0-9]+") || type == null) {
            return "You have send an incorrect price or notification type: " + text;
        }
        var subscriptionPrice = Double.parseDouble(text);

        var existedSubscription = subscribeRepository.findByUserIdAndNotificationType(user.getId(), type)
                .orElse(subscriptionMapper.telegramUserToSubscription(type, chatId, user));

        existedSubscription.setPrice(subscriptionPrice);
        existedSubscription.setNotificationType(type);
        subscribeRepository.save(existedSubscription);

        subscribeRepository.findByUserIdAndNotificationType(user.getId(), null).ifPresent(
                subscribeRepository::delete
        );

        return "You have been subscribed for " + type + " by BTC price: " + TextUtil.toString(subscriptionPrice) + " USD";
    }

    public String getSubscription(User user) {
        var existedSubscription = findByUserId(user.getId()).stream()
                .filter(subscription -> subscription.getPrice() != null)
                .toList();

        if (existedSubscription.isEmpty()) {
            return "You have no any subscription";
        }

        StringBuilder result = new StringBuilder("You have a subscription for BTC price: ");

        existedSubscription.forEach(subscription -> {
            result.append(subscription.getNotificationType().toString())
                    .append(": ").append(subscription.getPrice()).append(" USD\n");
        });

        return result.toString();
    }

    public String unsubscribe(User user, String text) {
        if (text.toUpperCase().contains("BUY")) {
            return unsubscribeByNotificationType(NotificationType.BUY, user);
        }
        if (text.toUpperCase().contains("SELL")) {
            return unsubscribeByNotificationType(NotificationType.SELL, user);
        }
        if (text.toUpperCase().contains("ALL")) {
            return unsubscribeAll(user);
        }
        return "Incorrect command, please try again";
    }

    private String unsubscribeByNotificationType(NotificationType type, User user) {
        var existedSubscription = subscribeRepository.findByUserIdAndNotificationType(user.getId(), type)
                .orElse(null);

        if (existedSubscription == null || existedSubscription.getPrice() == null) {
            return "You have no any subscription for " + type.toString() + " notification";
        }

        existedSubscription.setPrice(null);

        subscribeRepository.save(existedSubscription);

        return "Your subscription for " + type.toString() + " notification was canceled";
    }

    private String unsubscribeAll(User user) {
        var existedSubscription = subscribeRepository.findByUserId(user.getId());

        if (existedSubscription.isEmpty()) {
            return "You have no any subscription subscriptions";
        }

        existedSubscription.forEach(s -> s.setPrice(null));

        subscribeRepository.saveAll(existedSubscription);

        return "Your subscription was canceled";
    }

    private String preparePriceString(String price) {
        return price.trim().replaceAll("[^0-9]+", "");
    }

    private List<Subscription> findByUserId(Long userId) {
        return subscribeRepository.findByUserId(userId);
    }
}
