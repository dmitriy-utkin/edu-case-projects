package ru.skillbox.currency.exchange.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "create_sequence", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @ToString.Exclude
    private String name;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "value")
    private Double value;

    @Column(name = "iso_num_code")
    private Long isoNumCode;

    @Column(name = "iso_char_code")
    private String isoCharCode;

}
