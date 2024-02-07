package ru.practice.cryptobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.cryptobot.dao.Subscription;
import ru.practice.cryptobot.dto.NotificationType;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    Optional<Subscription> findByUserIdAndNotificationType(Long userId, NotificationType notificationType);

    boolean existsByUserId(Long userId);

    List<Subscription> findByNotificationTypeAndPriceGreaterThan(NotificationType notificationType, double price);

    List<Subscription> findByNotificationTypeAndPriceLessThan(NotificationType notificationType, double price);
}
