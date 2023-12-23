package airfreights.console.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Airport {
    private String airportCode;
    private String cityCode;
    private String countryCode;
    private String name;
}
