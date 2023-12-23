package airfreights.console.model;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Freight {
    private String freightNumber;
    private Date departureDate;
    private String departureTime;
    private String  flightTime;
    private Airport airportDeparture;
    private Airport airportArrival;
    private int flightCost;

    //TODO: сделать корректное сравнение по дате + времени
}
