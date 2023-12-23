package com.example.rest.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertClientRequest {

    @NotBlank(message = "Client name should be field")
    @Size(min = 3, max = 30, message = "Client name length should be between 3 and 30")
    private String name;
}
