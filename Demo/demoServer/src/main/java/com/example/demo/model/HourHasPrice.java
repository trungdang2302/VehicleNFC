package com.example.demo.model;

import java.util.List;

public class HourHasPrice {
    int hour;
    Double price;
    Double total;
    Integer minutes;

    public HourHasPrice(int hour, Double price) {
        this.hour = hour;
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int compare(HourHasPrice hourHasPrice) {
        return (this.getHour() < hourHasPrice.getHour()) ? -1 : 1;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public static List<HourHasPrice> sort(List<HourHasPrice> hourHasPrices) {

        hourHasPrices.sort((o1, o2) -> o1.compare(o2));

        return hourHasPrices;
    }
}
