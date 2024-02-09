package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.util.XMLParser;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyDownloaderService {

    private final RestTemplate restTemplate;

    private final CurrencyMapper currencyMapper;

    @Value("${app.xmlCurrencyListUrl}")
    private String xmlCurrencyListUrl;

    public List<Currency> getActualCurrencyList() {
        log.info("CurrencyDownloaderService method updateCurrencyList executed");
        String actualCurrencies = getActualCurrencies();
        return XMLParser.getXmlCurrencyListDto(actualCurrencies, currencyMapper);
    }

    private String getActualCurrencies() {
        return restTemplate.getForObject(xmlCurrencyListUrl, String.class);
    }
}
