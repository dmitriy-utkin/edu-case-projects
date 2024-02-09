package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Valute")
public class XmlCurrencyDto {

    private String name;

    private Long nominal;

    private String value;

    private Long numCode;

    private String charCode;

    @XmlElement(name = "Name", type = String.class)
    public String getName() {
        return name;
    }

    @XmlElement(name = "Nominal", type = Long.class)
    public Long getNominal() {
        return nominal;
    }

    @XmlElement(name = "Value", type = String.class)
    public String getValue() {
        return value;
    }

    @XmlElement(name = "NumCode", type = Long.class)
    public Long getNumCode() {
        return numCode;
    }

    @XmlElement(name = "CharCode", type = String.class)
    public String getCharCode() {
        return charCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNominal(Long nominal) {
        this.nominal = nominal;
    }

    public void setValue(String value) {
        this.value = value.replace(",", ".");
    }

    public void setNumCode(Long numCode) {
        this.numCode = numCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }
}
