package ru.education.restaurant.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tables")
@FieldNameConstants
public class Table {

    @Id
    private String id;

    @DBRef
    private Restaurant restaurant;

    private Integer number;

    private Set<LocalDateTime> reservedTimes = new HashSet<>();

    @Builder.Default
    @DBRef
    private List<Reservation> reservations = new ArrayList<>();
}
