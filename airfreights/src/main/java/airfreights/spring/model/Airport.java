package airfreights.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "airports")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("code")
    @Column(name = "airport_code", columnDefinition = "VARCHAR(255)", nullable = false)
    private String airportCode;

    @JsonProperty("city_code")
    @Column(name = "city_code", columnDefinition = "VARCHAR(255)", nullable = false)
    private String cityCode;

    @JsonProperty("country_code")
    @Column(name = "country_code", columnDefinition = "VARCHAR(255)", nullable = false)
    private String countryCode;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    private boolean flightable;

    @JsonProperty("time_zone")
    @Column(name = "time_zone", columnDefinition = "VARCHAR(255)", nullable = false)
    private String timeZone;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("iata_type")
    @Column(name = "iata_type", columnDefinition = "VARCHAR(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private IataType iataType;
}
