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
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
@FieldNameConstants
public class User {

    @Id
    private String id;

    private String email;

    private String password;

    @Builder.Default
    private PrivilegeLevel privilegeLevel = PrivilegeLevel.FIRST;

    @Builder.Default
    private BigDecimal bonusBalance = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal totalSpentMoney = BigDecimal.ZERO;

    @DBRef
    @Field("orders")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<Delivery> deliveries = new ArrayList<>();

    @Builder.Default
    private Set<Role> roles = Set.of(Role.USER);
}
