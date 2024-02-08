package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDtoList {

    @Builder.Default
    private List<CurrencyDto> currencies = new ArrayList<>();
}
