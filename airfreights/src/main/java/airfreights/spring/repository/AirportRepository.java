package airfreights.spring.repository;

import airfreights.spring.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    boolean existsByAirportCode(String airportCode);
    Optional<Airport> findByAirportCode(String airportCode);
}
