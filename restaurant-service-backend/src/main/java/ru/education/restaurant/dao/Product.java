package ru.education.restaurant.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
@FieldNameConstants
public class Product {

    @Id
    private String id;

    @DBRef
    private Restaurant restaurant;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer preparationTime;

    private Double weight;

    private Double energyValue;

    @DBRef
    private Category category;
}
