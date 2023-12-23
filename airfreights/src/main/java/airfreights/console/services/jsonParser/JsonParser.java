package airfreights.console.services.jsonParser;

import airfreights.console.model.AirportList;

public interface JsonParser {
    AirportList getAirportsListFromJson(String path);
}
