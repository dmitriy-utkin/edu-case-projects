package ru.practice.cryptobot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.practice.cryptobot.client.BinanceClient;
import ru.practice.cryptobot.dao.Subscription;
import ru.practice.cryptobot.exception.EntityNotFoundException;
import ru.practice.cryptobot.mapper.SubscriptionMapper;
import ru.practice.cryptobot.repository.SubscribeRepository;
import ru.practice.cryptobot.utils.TextUtil;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyService {

    private final AtomicReference<Double> price = new AtomicReference<>();

    private final BinanceClient client;

    private final SubscribeRepository subscribeRepository;

    private final SubscriptionMapper subscriptionMapper;

    public double getBitcoinPrice() throws IOException {
        if (price.get() == null) {
            price.set(client.getBitcoinPrice());
        }
        return price.get();
    }

    public Subscription saveNewUser(User user) {
        if (subscribeRepository.existsByUserId(user.getId())) {
            return findByUserId(user.getId());
        }
        return subscribeRepository.save(subscriptionMapper.telegramUserToSubscription(user));
    }

    public String subscribe(User user, String price) {

        price = preparePriceString(price);

        if (!price.matches("[0-9]+")) {
            return "You have send an incorrect price: " + price;
        }
        var subscriptionPrice = Double.parseDouble(price);

        var existedSubscription = subscribeRepository.findByUserId(user.getId()).orElse(saveNewUser(user));

        existedSubscription.setPrice(subscriptionPrice);
        subscribeRepository.save(existedSubscription);

        return "You have been subscribed for BTC price: " + TextUtil.toString(subscriptionPrice) + " USD";
    }

    public String getSubscription(User user) {
        var existedSubscription = findByUserId(user.getId());

        if (existedSubscription.getPrice() == null) {
            return "You have no any subscription";
        }

        return "You have a subscription for BTC price: " + TextUtil.toString(existedSubscription.getPrice()) + " USD";
    }

    public String unsubscribe(User user) {
        var existedSubscription = findByUserId(user.getId());

        if (existedSubscription.getPrice() == null) {
            return "You have no any subscription";
        }

        existedSubscription.setPrice(null);

        subscribeRepository.save(existedSubscription);

        return "Your subscription was canceled";
    }

    private String preparePriceString(String price) {
        return price.trim().replaceAll("[^0-9]+", "");
    }

    private Subscription findByUserId(Long userId) {
        return subscribeRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
    }
}
