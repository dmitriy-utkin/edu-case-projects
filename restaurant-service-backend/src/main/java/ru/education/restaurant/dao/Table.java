package ru.education.restaurant.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@jakarta.persistence.Table(name = "tables")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Restaurant restaurant;

    private Integer number;

    @Column(name = "reserved_times")
    @Builder.Default
    @ToString.Exclude
    private Set<LocalDateTime> reservedTimes = new HashSet<>();

    @OneToMany(mappedBy = "table")
    @Builder.Default
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "table")
    @Builder.Default
    @JsonIgnore
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();


}
