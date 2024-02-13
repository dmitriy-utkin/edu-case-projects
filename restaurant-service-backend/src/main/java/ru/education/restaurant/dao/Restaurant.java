package ru.education.restaurant.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@jakarta.persistence.Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String headline;

    private String city;

    @ElementCollection(targetClass = OrderType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "restaurant_order_types", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "available_order_types")
    @Enumerated(EnumType.STRING)
    private Set<OrderType> orderTypes;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    @Builder.Default
    private List<Table> tables = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    @Builder.Default
    private List<Delivery> deliveries = new ArrayList<>();
}
