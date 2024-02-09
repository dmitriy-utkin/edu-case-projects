package ru.skillbox.currency.exchange.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.currency.exchange.dto.*;
import ru.skillbox.currency.exchange.entity.Currency;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(CurrencyMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    SimpleCurrencyDto convertToSimpleDto(Currency currency);

    Currency convertToEntity(CurrencyDto currencyDto);

    Currency convertToCurrency(XmlCurrencyDto currencyDto);

    default List<Currency> convertToCurrencyList(XmlCurrencyListDto xmlCurrencyListDto) {
        return xmlCurrencyListDto.getValutes().stream()
                .map(this::convertToCurrency)
                .collect(Collectors.toList());
    }

    default CurrencyDtoList convertToDtoList(List<Currency> currencies) {
        return CurrencyDtoList.builder()
                .currencies(currencies.stream().map(this::convertToSimpleDto).collect(Collectors.toList()))
                .build();
    }
}
