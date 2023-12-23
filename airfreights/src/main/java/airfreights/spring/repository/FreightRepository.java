package airfreights.spring.repository;

import airfreights.spring.model.Freight;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface FreightRepository extends JpaRepository<Freight, Integer> {

    @EntityGraph(attributePaths = {"airportDeparture", "airportArrival"})
    Optional<Freight> findByFreightNumber(String freightNumber);

    @Override
    @EntityGraph(attributePaths = {"airportDeparture", "airportArrival"})
    List<Freight> findAll();

    boolean existsByFreightNumber(String freightNumber);
    Optional<List<Freight>> findByDepartureTime(Time time);
}
