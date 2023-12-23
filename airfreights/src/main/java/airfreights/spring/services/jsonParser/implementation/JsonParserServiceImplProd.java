package airfreights.spring.services.jsonParser.implementation;

import airfreights.spring.config.ParserConfig;
import airfreights.spring.model.Airport;
import airfreights.spring.model.IataType;
import airfreights.spring.repository.AirportRepository;
import airfreights.spring.services.response.implementation.ResponseServiceImpl;
import airfreights.spring.services.jsonParser.JsonParserService;
import airfreights.spring.services.response.ResponseService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "prod", matchIfMissing = true)
public class JsonParserServiceImplProd implements JsonParserService {

    private final AirportRepository airportRepository;
    private final ParserConfig parserConfig;

    @Override
    public ResponseEntity<ResponseService> updateAirportsBaseFromJson() {
        clearDataBase();
        long start = System.currentTimeMillis();
        List<Airport> airports;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            airports = objectMapper.readValue(new File(parserConfig.getJsonFilePath()), new TypeReference<>() {});
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseServiceImpl.ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long end = System.currentTimeMillis();
        airportRepository.saveAllAndFlush(airports);
        log.info("Parsed in PROD profile");
        if (parserConfig.isResponseWithMessage()) return new ResponseEntity<>(new ResponseServiceImpl
                    .SuccessResponseWithMessage("Time is ms: " + (end - start)), HttpStatus.OK);
        return new ResponseEntity<>(new ResponseServiceImpl
                .SuccessResponse(), HttpStatus.OK);
    }


    private void clearDataBase() {
        airportRepository.deleteAllInBatch(); log.info("Airports was deleted.");
    }
}
