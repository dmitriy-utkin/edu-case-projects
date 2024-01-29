package ru.practice.cryptobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practice.cryptobot.dao.Subscribe;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
}
