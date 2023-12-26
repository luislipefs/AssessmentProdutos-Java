package br.com.at_produtos.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyInfo {
    private double price;
    private long timestamp;

    public double getPrice() {
        return price;
    }

    @JsonProperty("USD_BRL")
    private CurrencyInfo usdBrl;

    public CurrencyInfo getUsdBrl() {
        return usdBrl;
    }

    public void setUsdBrl(CurrencyInfo usdBrl) {
        this.usdBrl = usdBrl;
    }
}