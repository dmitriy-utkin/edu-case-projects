package airfreights.spring.services.freightSystem;

import airfreights.spring.dto.FreightView;
import airfreights.spring.model.Freight;
import airfreights.spring.services.response.ResponseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FreightsService {
    ResponseEntity<ResponseService> addFreight(String flightNumber,
                                               String departureDate,
                                               String departureTime,
                                               String airportDeparture,
                                               String airportArrival,
                                               String flightTime,
                                               String flightCost);
    ResponseEntity<ResponseService> getAllFreights();
    List<FreightView> getAllFreightsView();
    ResponseEntity<ResponseService> getFreightByNumber(String number);
}
