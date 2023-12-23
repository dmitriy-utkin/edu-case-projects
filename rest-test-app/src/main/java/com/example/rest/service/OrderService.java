package com.example.rest.service;

import com.example.rest.exception.UpdateStateException;
import com.example.rest.model.Order;
import com.example.rest.web.model.OrderFilter;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface OrderService {

    List<Order> filterBy(OrderFilter filter);
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    Order update(Order order);
    void deleteById(Long id);
    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Long orderId) {
        int limit = 5;
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrder.getUpdateAt(), now);
        if (duration.getSeconds() > limit) {
            throw new UpdateStateException(MessageFormat
                    .format("Order unavailable for update, it was changed more than {0} sec ago", limit));
        }
    }
}
