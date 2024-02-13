package ru.education.restaurant.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "full_price")
    private BigDecimal fullPrice;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_order_time")
    private LocalDateTime deliveryOrderTime;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "delivery_products", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonIgnore
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
