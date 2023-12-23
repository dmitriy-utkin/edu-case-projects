package airfreights.spring.services.freightSystem.implementation;

import airfreights.spring.dto.FreightView;
import airfreights.spring.model.Airport;
import airfreights.spring.model.Freight;
import airfreights.spring.repository.AirportRepository;
import airfreights.spring.repository.FreightRepository;
import airfreights.spring.services.freightSystem.FreightsService;
import airfreights.spring.services.response.ResponseService;
import airfreights.spring.services.response.implementation.ResponseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FreightsServiceImpl implements FreightsService {

    private final AirportRepository airportRepository;
    private final FreightRepository freightRepository;

    @Override
    public ResponseEntity<ResponseService> addFreight(String flightNumber, String departureDate, String departureTime,
                                                      String airportDeparture, String airportArrival,
                                                      String flightTime, String flightCost) {
        Map<Boolean, String> checkingResult = checkInputData(flightNumber, departureDate, departureTime,
                airportDeparture, airportArrival, flightTime, flightCost);
        if (checkingResult.containsKey(false)) return new ResponseEntity<>(new ResponseServiceImpl
                .ErrorResponse(checkingResult.get(false)), HttpStatus.NOT_ACCEPTABLE);
        Optional<Airport> departure = airportRepository.findByAirportCode(airportDeparture.toUpperCase());
        Optional<Airport> arrival = airportRepository.findByAirportCode(airportArrival.toUpperCase());
        if (departure.isPresent() && arrival.isPresent()) {
            Time time = createTime(departureTime);
            if (!isTimeIsAvailableForDirection(time, departure.get(), arrival.get()))
                return new ResponseEntity<>(new ResponseServiceImpl
                        .ErrorResponse("This time is not available for direction "
                        + airportDeparture.toUpperCase() + " - " + airportArrival.toUpperCase()),
                        HttpStatus.NOT_ACCEPTABLE);
            freightRepository.save(Freight.builder()
                    .freightNumber(flightNumber.toUpperCase())
                    .departureDate(new java.sql.Date(Objects.requireNonNull(createDate(departureDate)).getTime()))
                    .departureTime(time)
                    .airportDeparture(departure.get())
                    .airportArrival(arrival.get())
                    .flightTime(getCorrectFlightTime(flightTime))
                    .flightCost(Float.parseFloat(flightCost))
                    .build());
        }

        return new ResponseEntity<>(new ResponseServiceImpl.SuccessResponse(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseService> getAllFreights() {
        List<Freight> freightList = freightRepository.findAll();
        return freightList.isEmpty() ? new ResponseEntity<>(new ResponseServiceImpl
                .ErrorResponse("Nothing to show here. No any flights."), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new ResponseServiceImpl.SuccessFreightListResponse(freightList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseService> getFreightByNumber(String number) {
        Optional<Freight> freightOptional = freightRepository.findByFreightNumber(number.toUpperCase());
        return freightOptional.<ResponseEntity<ResponseService>>map(freight -> new ResponseEntity<>(
                new ResponseServiceImpl.SuccessFreightResponse(freight), HttpStatus.OK)).orElse(
                        new ResponseEntity<>(new ResponseServiceImpl
                                .ErrorResponse("Flight with number " + number + " was not found."), HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public List<FreightView> getAllFreightsView() {
        List<FreightView> freightViewList = freightRepository.findAll().stream().map(this::createFreightView).toList();
        return new ArrayList<>(freightViewList);
    }

    private FreightView createFreightView(Freight item) {
        return FreightView.builder()
                .freightNumber(item.getFreightNumber())
                .departureDate(item.getDepartureDate().toString())
                .departureTime(item.getDepartureTime().toString())
                .flightTime(String.valueOf(item.getFlightTime()))
                .airportDeparture(item.getAirportDeparture().getAirportCode())
                .airportArrival(item.getAirportArrival().getAirportCode())
                .flightCost(String.valueOf(item.getFlightCost()))
                .build();
    }

    private Map<Boolean, String> checkInputData(String flightNumber,
                                                String departureDate,
                                                String departureTime,
                                                String airportDeparture,
                                                String airportArrival,
                                                String flightTime,
                                                String flightCost) {
        if (freightRepository.existsByFreightNumber(flightNumber))
            return Map.of(false, "This freight already existed.");
        if (!isCorrectFlightNumber(flightNumber)) return Map.of(false, "Incorrect flightNumber");
        java.util.Date date = createDate(departureDate);
        if (date == null) return Map.of(false, "Incorrect Date");
        Time time = createTime(departureTime);
        if (time == null) return Map.of(false, "Incorrect Time");
        if (!isCorrectAirport(airportDeparture)) return Map.of(false, "Incorrect airportDeparture");
        if (!isCorrectAirport(airportArrival)) return Map.of(false, "Incorrect airportArrival");
        if (airportDeparture.equalsIgnoreCase(airportArrival))
            return Map.of(false, "AirportDeparture can`t be equal to AirportArrival");
        if (!isCorrectFlightTime(flightTime)) return Map.of(false, "Incorrect flightTime");
        if (!isCorrectFlightCost(flightCost)) return Map.of(false, "Incorrect fightCost");
        return Map.of(true, "Freight data is correct");
    }

    private boolean isCorrectFlightNumber(String flightNumber) {
        return flightNumber.matches("[a-zA-Z0-9]{4}");
    }

    private boolean isCorrectFlightTime(String flightTime) {
        return flightTime.matches("[0-9]{2}.[0-5][0-9]");
    }

    private boolean isCorrectAirport(String airportCode) {
        return airportRepository.existsByAirportCode(airportCode.toUpperCase(Locale.ROOT));
    }

    private boolean isTimeIsAvailableForDirection(Time time, Airport departure, Airport arrival) {
        Optional<List<Freight>> existedByTime = freightRepository.findByDepartureTime(time);
        if (existedByTime.isPresent()) {
            for (Freight freight : existedByTime.get()) {
                if (departure.getId() == freight.getAirportDeparture().getId()
                        && arrival.getId() == freight.getAirportArrival().getId()) return false;
            }
        }
        return true;
    }

    private boolean isCorrectFlightCost(String flightCost) {
        return (flightCost.matches("[0-9]+.?[0-9]+?") && Float.parseFloat(flightCost) > 0);
    }

    private java.util.Date createDate(String departureDate) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date date = simpleDate.parse(departureDate);
            return (Integer.parseInt(departureDate.split("/")[1]) > 12) ? null : date;
        } catch (ParseException parseException) {
            log.error(parseException.getMessage());
            return null;
        }
    }

    private Time createTime(String departureTime) {
        if (!departureTime.matches("[0-9]{2}:[0-5][0-9]")) return null;
        return Time.valueOf(departureTime + ":00");
    }

    private double getCorrectFlightTime(String flightTime) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(Float.parseFloat(flightTime));
        return Double.parseDouble(formatted.replace(",", "."));
    }

}
