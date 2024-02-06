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

    @Value("${app.notification.ttl}")
    private Long ttl;

    private final RedisTemplate<String, Instant> redisTemplate;

    public boolean userIsNotified(String username, NotificationType type) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(username + type));
    }

    public void updateNotificationList(String username, NotificationType type) {
        Duration durationTtl = Duration.ofMinutes(ttl);
        redisTemplate.opsForValue().set(username + type, Instant.now(), durationTtl);
    }
}
