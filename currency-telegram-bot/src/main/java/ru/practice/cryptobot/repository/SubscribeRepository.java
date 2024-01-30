package ru.practice.cryptobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.cryptobot.dao.Subscription;

import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserName(String userName);

    Optional<Subscription> findByUserId(Long userId);

    boolean existsByUserName(String userName);

    boolean existsByUserId(Long userId);
}
