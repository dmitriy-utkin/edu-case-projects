package airfreights.spring.controllers;

import airfreights.spring.services.freightSystem.FreightsService;
import airfreights.spring.services.jsonParser.JsonParserService;
import airfreights.spring.services.response.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ApiControllers {

    private final JsonParserService jsonParserService;
    private final FreightsService freightsService;

    @GetMapping("/updateAirports")
    public ResponseEntity<ResponseService> startJsonReadNew() {
        return jsonParserService.updateAirportsBaseFromJson();
    }

    @PostMapping("/addFreight")
    public ResponseEntity<ResponseService> addFreight(@RequestParam String flightNumber,
                                                      @RequestParam String departureDate,
                                                      @RequestParam String departureTime,
                                                      @RequestParam String airportDeparture,
                                                      @RequestParam String airportArrival,
                                                      @RequestParam String flightTime,
                                                      @RequestParam String flightCost) {
        return freightsService.addFreight(flightNumber, departureDate, departureTime, airportDeparture,
                                            airportArrival, flightTime, flightCost);
    }

    @GetMapping("/getAllFreights")
    public ResponseEntity<ResponseService> getAllFreights() {
        return freightsService.getAllFreights();
    }


    @GetMapping("/getFreight")
    public ResponseEntity<ResponseService> getFreight(@RequestParam String number) {
        return freightsService.getFreightByNumber(number);
    }


}
