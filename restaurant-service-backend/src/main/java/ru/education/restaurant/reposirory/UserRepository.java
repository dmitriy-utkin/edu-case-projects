package ru.education.restaurant.reposirory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.education.restaurant.dao.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
