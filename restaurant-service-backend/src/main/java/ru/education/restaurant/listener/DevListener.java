package ru.education.restaurant.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.education.restaurant.dao.Order;
import ru.education.restaurant.dao.OrderType;
import ru.education.restaurant.dao.Product;
import ru.education.restaurant.dao.User;
import ru.education.restaurant.reposirory.OrderRepository;
import ru.education.restaurant.reposirory.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DevListener {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void updateDb() {
        log.info("DevListener method updateDb was executed");
        var user = userRepository.save(User.builder()
                        .id(UUID.randomUUID().toString())
                        .email("some2@email.com")
                        .password("somePass")
                .build()).block();

        var order = orderRepository.save(Order.builder()
                        .id(UUID.randomUUID().toString())
                        .user(user)
                        .fullPrice(BigDecimal.valueOf(100))
                        .table(null)
                        .products(Collections.emptyList())
                .build()).block();
    }
}
