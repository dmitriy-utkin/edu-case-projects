package ru.skillbox.currency.exchange.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.skillbox.currency.exchange.dto.XmlCurrencyListDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

@UtilityClass
public class XMLParser {

    @SneakyThrows
    public static List<Currency> getCurrencyList(String xml, CurrencyMapper currencyMapper) {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlCurrencyListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlCurrencyListDto xmlCurrencyListDto = (XmlCurrencyListDto) unmarshaller.unmarshal(new StringReader(xml));
        return currencyMapper.convertToCurrencyList(xmlCurrencyListDto);
    }
}
