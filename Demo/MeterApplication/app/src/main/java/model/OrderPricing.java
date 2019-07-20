package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderPricing {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("fromHour")
    @Expose
    private int fromHour;

    @SerializedName("pricePerHour")
    @Expose
    private double pricePerHour;

    @SerializedName("lateFeePerHour")
    @Expose
    private double lateFeePerHour;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getLateFeePerHour() {
        return lateFeePerHour;
    }

    public void setLateFeePerHour(double lateFeePerHour) {
        this.lateFeePerHour = lateFeePerHour;
    }

    public static List<Pricing> convert(List<OrderPricing> orderPricings) {
        List<Pricing> pricings = new ArrayList<>();
        for (OrderPricing orderPricing : orderPricings) {
            Pricing pricing = new Pricing();
            pricing.setFromHour(orderPricing.getFromHour());
            pricing.setPricePerHour(orderPricing.getPricePerHour());
            pricing.setLateFeePerHour(orderPricing.getLateFeePerHour());

            pricings.add(pricing);
        }
        return pricings;
    }
}
