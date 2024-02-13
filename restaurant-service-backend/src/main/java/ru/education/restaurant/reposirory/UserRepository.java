package ru.education.restaurant.reposirory;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.education.restaurant.dao.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
