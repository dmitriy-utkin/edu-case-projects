package ru.skillbox.currency.exchange.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyDtoList;
import ru.skillbox.currency.exchange.entity.Currency;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    Currency convertToEntity(CurrencyDto currencyDto);

    default CurrencyDtoList convertToDtoList(List<Currency> currencies) {
        return CurrencyDtoList.builder()
                .currencies(currencies.stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }
}
