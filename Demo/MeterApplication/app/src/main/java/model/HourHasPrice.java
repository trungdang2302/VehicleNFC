package model;

public class HourHasPrice {
    int hour;
    Double price;

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

    public void setPrice(Double price) {
        this.price = price;
    }
}
