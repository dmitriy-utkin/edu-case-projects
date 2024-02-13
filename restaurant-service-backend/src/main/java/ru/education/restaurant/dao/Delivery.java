package ru.education.restaurant.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.education.restaurant.dao.enums.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "deliveries")
@FieldNameConstants
public class Delivery {

    @Id
    private Long id;

    @DBRef
    private User user;

    @Builder.Default
    private OrderType orderType = OrderType.DELIVERY;

    private BigDecimal fullPrice;

    private String deliveryAddress;

    private LocalDateTime deliveryOrderTime;

    @DBRef
    private Restaurant restaurant;

    @DBRef
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
