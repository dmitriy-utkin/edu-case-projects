package ru.skillbox.currency.exchange.mapper;

import ru.skillbox.currency.exchange.dto.XmlCurrencyDto;
import ru.skillbox.currency.exchange.entity.Currency;

public abstract class CurrencyMapperDelegate implements CurrencyMapper {

    @Override
    public Currency convertToCurrency(XmlCurrencyDto currencyDto) {
        return Currency.builder()
                .name(currencyDto.getName())
                .value(Double.valueOf(currencyDto.getValue()))
                .nominal(currencyDto.getNominal())
                .isoCharCode(currencyDto.getCharCode())
                .isoNumCode(currencyDto.getNumCode())
                .build();
    }
}
