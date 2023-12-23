package airfreights.spring.dto;

import lombok.Builder;

@Builder
public record FreightView(String freightNumber, String departureDate,
                          String departureTime, String flightTime,
                          String airportDeparture, String airportArrival, String flightCost) {}