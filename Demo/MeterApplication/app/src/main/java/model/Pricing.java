package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pricing {


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
}
