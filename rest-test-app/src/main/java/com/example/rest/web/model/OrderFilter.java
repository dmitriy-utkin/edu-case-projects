package com.example.rest.web.model;

import com.example.rest.validation.OrderFilterValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@OrderFilterValid
public class OrderFilter {

    private Integer pageSize;

    private Integer pageNum;

    private String productName;

    private BigDecimal minCost;

    private BigDecimal maxCost;

    private Instant createdBefore;

    private Instant updatedBefore;

    private Long clientId;
}
