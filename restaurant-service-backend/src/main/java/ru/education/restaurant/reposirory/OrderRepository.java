package ru.education.restaurant.reposirory;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.education.restaurant.dao.Order;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
