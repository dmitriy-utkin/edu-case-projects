package ru.practice.cryptobot.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.practice.cryptobot.dto.NotificationType;

import java.time.Duration;
import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    @Value("${telegram.bot.notify.delay.value}")
    private Long ttlValue;

    @Value("${telegram.bot.notify.delay.unit}")
    private String ttlUnit;

    private final RedisTemplate<String, Instant> redisTemplate;

    public boolean userIsNotified(String username, NotificationType type) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(username + type));
    }

    public void updateNotificationList(String username, NotificationType type) {
        redisTemplate.opsForValue().set(username + type, Instant.now(), getDuration());
    }

    private Duration getDuration() {
        if (ttlUnit.toUpperCase().contains("MIN")) {
            return Duration.ofMinutes(ttlValue);
        }
        if (ttlUnit.toUpperCase().contains("SEC")) {
            return Duration.ofSeconds(ttlValue);
        }
        if (ttlUnit.toUpperCase().contains("HOUR")) {
            return Duration.ofHours(ttlValue);
        }
        throw new IllegalArgumentException("Illegal duration type");
    }
}
