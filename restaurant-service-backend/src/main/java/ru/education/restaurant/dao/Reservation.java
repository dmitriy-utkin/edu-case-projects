package ru.education.restaurant.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@jakarta.persistence.Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime from;

    private LocalDateTime to;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Table table;

}
