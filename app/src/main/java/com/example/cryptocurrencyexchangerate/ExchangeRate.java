package com.example.cryptocurrencyexchangerate;

/**
 * Created by salabs on 23/10/2017.
 */

public class ExchangeRate {
    private double cryptoCurrency;
    private double fiatCurrency;

    public ExchangeRate(double cryptoCurrency, double fiatCurrency) {
        this.cryptoCurrency = cryptoCurrency;
        this.fiatCurrency = fiatCurrency;
    }

    public ExchangeRate(double fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }

    public double getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(double cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public double getFiatCurrency() {
        return fiatCurrency;
    }

    public void setFiatCurrency(double fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }
}
