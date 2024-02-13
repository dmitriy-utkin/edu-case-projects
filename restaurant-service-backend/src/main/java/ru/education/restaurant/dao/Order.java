package ru.education.restaurant.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "orders")
@FieldNameConstants
public class Order {

    @Id
    private String id;

    @DBRef
    private Table table;

    @Builder.Default
    private OrderType orderType = OrderType.ON_PLACE;

    private BigDecimal fullPrice;

    @DBRef
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @DBRef
    @Field("users")
    private User user;
}
