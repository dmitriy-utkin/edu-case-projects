package com.example.rest.repository;

import com.example.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DatabaseOrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

//    Page<Order> findAllByProduct(String product, Pageable pageable);

    @Query("SELECT o FROM com.example.rest.model.Order o WHERE o.product = :productName") // JPQL
    List<Order> getByProductJpql(String productName);

    @Query(value = "SELECT * FROM orders o WHERE o.product = :productName", nativeQuery = true) // Native SQL
    List<Order> getByProductSql(String productName);
}

