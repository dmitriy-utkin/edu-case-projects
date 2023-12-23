package airfreights.spring.services.jsonParser.implementation;

import airfreights.spring.config.ParserConfig;
import airfreights.spring.model.Airport;
import airfreights.spring.model.IataType;
import airfreights.spring.repository.AirportRepository;
import airfreights.spring.services.jsonParser.JsonParserService;
import airfreights.spring.services.response.ResponseService;
import airfreights.spring.services.response.implementation.ResponseServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "test")
public class JsonParserServiceImplTest implements JsonParserService {

    private final AirportRepository airportRepository;
    private final ParserConfig parserConfig;

    @Override
    public ResponseEntity<ResponseService> updateAirportsBaseFromJson() {
        clearDataBase();
        List<Airport> airports = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        long start = System.currentTimeMillis();
        try {
            JsonNode rootNode = mapper.readTree(new File(parserConfig.getJsonFilePath()));
            rootNode.forEach(node -> airports.add(createAirportEntity(node)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseServiceImpl
                    .ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long end = System.currentTimeMillis();
        airportRepository.saveAllAndFlush(airports);
        log.info("Parsed in TEST profile");
        if (parserConfig.isResponseWithMessage()) return new ResponseEntity<>(new ResponseServiceImpl
                .SuccessResponseWithMessage("Time is ms: " + (end - start)), HttpStatus.OK);
        return new ResponseEntity<>(new ResponseServiceImpl
                .SuccessResponse(), HttpStatus.OK);
    }

    private void clearDataBase() {
        airportRepository.deleteAllInBatch(); log.info("Airports was deleted.");
    }

    private IataType getIataType(String iataString) {
        if (iataString.equals("airport")) return IataType.airport;
        if (iataString.equals("railway")) return IataType.railway;
        if (iataString.equals("bus")) return IataType.bus;
        if (iataString.equals("military")) return IataType.military;
        if (iataString.equals("seaplane")) return IataType.seaplane;
        if (iataString.equals("harbour")) return IataType.harbour;
        if (iataString.equals("heliport")) return IataType.heliport;
        if (iataString.equals("airline")) return IataType.airline;
        return null;
    }

    private Airport createAirportEntity(JsonNode node) {
        return Airport.builder()
                .name(node.get("name").asText())
                .airportCode(node.get("code").asText())
                .cityCode(node.get("city_code").asText())
                .countryCode(node.get("country_code").asText())
                .iataType(getIataType(node.get("iata_type").asText()))
                .flightable(node.get("flightable").asBoolean())
                .timeZone(node.get("time_zone").asText())
                .build();
    }

}
