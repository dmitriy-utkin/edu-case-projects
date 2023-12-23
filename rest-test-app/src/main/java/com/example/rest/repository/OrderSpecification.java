package com.example.rest.repository;

import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.web.model.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;

public interface OrderSpecification {

    static Specification<Order> withFilter(OrderFilter orderFilter) {
        return Specification.where(byProductName(orderFilter.getProductName()))
                .and(byCostTage(orderFilter.getMinCost(), orderFilter.getMaxCost()))
                .and(byClientId(orderFilter.getClientId()))
                .and(byCreatedAtBefore(orderFilter.getCreatedBefore()))
                .and(byUpdatedAtBefore(orderFilter.getUpdatedBefore()));
    }

    static Specification<Order> byUpdatedAtBefore(Instant updatedBefore) {
        return (root, query, cb) -> {
            if (updatedBefore == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get(Order.Fields.updateAt), updatedBefore);
        };
    }


    static Specification<Order> byCreatedAtBefore(Instant createdBefore) {
        return (root, query, cb) -> {
            if (createdBefore == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get(Order.Fields.createAt), createdBefore);
        };
    }


    static Specification<Order> byClientId(Long clientId) {
        return (root, query, cb) -> {
            if (clientId == null) {
                return null;
            }

            return cb.equal(root.get(Order.Fields.client).get(Client.Fields.id), clientId);
        };
    }

    static Specification<Order> byCostTage(BigDecimal minCost, BigDecimal maxCost) {
        return (root, query, cb) -> {
            if (minCost == null && maxCost == null) {
                return null;
            }

            if (minCost == null) {
                return cb.lessThanOrEqualTo(root.get(Order.Fields.cost), maxCost);
            }

            if (maxCost == null) {
                return cb.greaterThanOrEqualTo(root.get(Order.Fields.cost), minCost);
            }

            return cb.between(root.get(Order.Fields.cost), minCost, maxCost);
        };
    }

    static Specification<Order> byProductName(String productName) {
        return (root, query, cb) -> {
            if (productName == null) {
                return null;
            }
            return cb.equal(root.get(Order.Fields.product), productName);
        };
    }
}
