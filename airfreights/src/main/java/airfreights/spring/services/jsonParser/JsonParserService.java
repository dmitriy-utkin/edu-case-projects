package airfreights.spring.services.jsonParser;

import airfreights.spring.services.response.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface JsonParserService {
    ResponseEntity<ResponseService> updateAirportsBaseFromJson();
}
