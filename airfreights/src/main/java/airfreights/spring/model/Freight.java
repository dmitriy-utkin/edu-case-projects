package airfreights.spring.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "freights", indexes = {@Index(name = "freight_number", columnList = "freight_number", unique = true)})
public class Freight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "freight_number", columnDefinition = "VARCHAR(255)", nullable = false)
    private String freightNumber;

    @Column(name = "departure_date", nullable = false)
    private Date departureDate;

    @Column(name = "departure_time", nullable = false)
    private Time departureTime;

    @Column(name = "flight_time", nullable = false)
    private double flightTime;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "airport_departure_id", nullable = false)
    private Airport airportDeparture;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "airport_arrival_id", nullable = false)
    private Airport airportArrival;

    @Column(name = "flight_cost", nullable = false)
    private float flightCost;
}
