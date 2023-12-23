package airfreights.console.dto;

import airfreights.console.model.Airport;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class FreightDto {
    private String freightNumber;
    private String departureDate;
    private String departureTime;
    private String flightTime;
    private String airportDeparture;
    private String airportArrival;
    private String flightCost;
    private boolean isValidData;
    private String error;
}
