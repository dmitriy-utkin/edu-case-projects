package ru.education.restaurant.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "restaurants")
@FieldNameConstants
public class Restaurant {

    @Id
    private String id;

    private String name;

    private String headline;

    private String address;

    private String city;

    @Builder.Default
    private Set<OrderType> orderTypes = Set.of(OrderType.DELIVERY, OrderType.ON_PLACE);

    @Builder.Default
    @DBRef
    private Set<Table> tables = new HashSet<>();

    @Builder.Default
    @DBRef
    private Set<Product> products = new HashSet<>();
}
