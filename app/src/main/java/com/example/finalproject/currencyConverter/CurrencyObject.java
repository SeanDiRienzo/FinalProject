package com.example.finalproject.currencyConverter;

public class CurrencyObject {
    private String name;
    private String rate;

    public CurrencyObject(String name, String rate){
        setName(name);
        setRate(rate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
