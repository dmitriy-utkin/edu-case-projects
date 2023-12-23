package airfreights.spring.services.jsonParser.implementation;

import airfreights.spring.services.jsonParser.JsonParserService;
import airfreights.spring.services.response.ResponseService;
import airfreights.spring.services.response.implementation.ResponseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "other")
public class JsonParserServiceImplOther implements JsonParserService {
    @Override
    public ResponseEntity<ResponseService> updateAirportsBaseFromJson() {
        log.info("Parser was launched in the \"OTHER\" profile, nothing to show.");
        return new ResponseEntity<>(new ResponseServiceImpl
                .ErrorResponse("AIRPORTS PARSER UNDER CONSTRUCTION"), HttpStatus.I_AM_A_TEAPOT);
    }
}
