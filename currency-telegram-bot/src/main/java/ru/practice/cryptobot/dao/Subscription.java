package ru.practice.cryptobot.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practice.cryptobot.dto.NotificationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subscribes", indexes = @Index(name = "user_id_type_index", columnList = "userId, notificationType", unique = true))
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String userName;

    private Double price;

    private Long chatId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

}
