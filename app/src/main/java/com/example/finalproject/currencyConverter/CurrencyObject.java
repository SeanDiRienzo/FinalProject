package com.example.finalproject.currencyConverter;

public class CurrencyObject {
    private String base;
    private String name;
    private String rate;

    public CurrencyObject(String name, String rate, String base){
        setName(name);
        setRate(rate);
        setBase(base);
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


    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
