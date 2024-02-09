package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyDtoList;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.exception.EntityNotFoundException;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
import ru.skillbox.currency.exchange.util.BeanUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyMapper mapper;

    private final CurrencyRepository repository;

    private final CurrencyDownloaderService currencyDownloaderService;

    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public CurrencyDtoList getAll() {
        log.info("CurrencyService method getAll executed");
        List<Currency> currencies = repository.findAll();
        return mapper.convertToDtoList(currencies);
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return  mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public Currency findByIsoNumCode(Long isoNumCode) {
        return repository.findByIsoNumCode(isoNumCode).orElseThrow(
                () -> new EntityNotFoundException("Currency with iso num code " + isoNumCode + " is not found")
        );
    }

    public Currency findByIsoCharCode(String isoCharCode) {
        return repository.findByIsoCharCode(isoCharCode).orElseThrow(
                () -> new EntityNotFoundException("Currency with iso char code " + isoCharCode + " is not found")
        );
    }

    @Scheduled(fixedRate = 3_600_000)
    public void updateCurrencyTable() {

        List<Currency> newCurrencies = currencyDownloaderService.getActualCurrencyList();

        newCurrencies.forEach(
                currency -> {
                    try {
                        Currency existedCurrency = findByIsoCharCode(currency.getIsoCharCode());
                        BeanUtils.copyNonNullProperties(currency, existedCurrency);
                        repository.save(existedCurrency);
                        log.info("Updated existed currency: {}", existedCurrency);
                    }
                    catch (EntityNotFoundException e) {
                        repository.save(currency);
                        log.info("Detected and saved a new currency: {}", currency);
                    }
                }
        );
    }
}
