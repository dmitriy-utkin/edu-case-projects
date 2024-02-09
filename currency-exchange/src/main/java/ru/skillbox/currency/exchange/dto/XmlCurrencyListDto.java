package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ValCurs")
public class XmlCurrencyListDto {

    private List<XmlCurrencyDto> valutes;

    @XmlElement(name = "Valute")
    public List<XmlCurrencyDto> getValutes() {
        return valutes;
    }

    public void setValutes(List<XmlCurrencyDto> valutes) {
        this.valutes = valutes;
    }
}
