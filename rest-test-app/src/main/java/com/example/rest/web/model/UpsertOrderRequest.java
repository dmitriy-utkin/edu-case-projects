package com.example.rest.web.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertOrderRequest {

    @NotNull(message = "Client ID should be field")
    @Positive(message = "Client ID should be more than zero")
    private Long clientId;

    @NotBlank(message = "Product name should be field")
    @Size(min = 3, max = 200, message = "Product name should be more than 3 and less than 200")
    private String product;

    @NotNull(message = "Cost should be field")
    @PositiveOrZero(message = "Cost should be more or equals to zero")
    private BigDecimal cost;
}
