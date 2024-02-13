package ru.education.restaurant.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(name = "privilege_level")
    private PrivilegeLevel privilegeLevel;

    @Column(name = "bonus_balance")
    @Enumerated(EnumType.STRING)
    private BigDecimal bonusBalance;

    @Column(name = "total_spent_money")
    private BigDecimal totalSpentMoney;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonIgnore
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
