package ru.education.restaurant.dao;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Restaurant restaurant;

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "preparation_time")
    private Integer preparationTime;

    private Double weight;

    @Column(name = "energy_value")
    private Double energyValue;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Category category;

}
