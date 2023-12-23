package airfreights.console.services.jsonParser;

import airfreights.console.model.AirportList;
import airfreights.console.model.Airport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonParserImpl implements JsonParser{
    @Override
    public AirportList getAirportsListFromJson(String path) {
        AirportList airports = new AirportList();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(path));
            List<Airport> airportList = new ArrayList<>();
            rootNode.forEach(node -> {
                airportList.add(createAirportEntity(node));
            });
            if (airportList.size() > 0) airports.setAirportList(airportList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airports;
    }

    private Airport createAirportEntity(JsonNode node) {
        return Airport.builder()
                .name(node.get("name").asText())
                .airportCode(node.get("code").asText())
                .cityCode(node.get("city_code").asText())
                .countryCode(node.get("country_code").asText())
                .build();
    }
}
