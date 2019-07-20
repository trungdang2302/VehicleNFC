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

    @SerializedName("toHour")
    @Expose
    private int toHour;

    @SerializedName("pricePerHour")
    @Expose
    private double pricePerHour;

    @SerializedName("lateFeePerHour")
    @Expose
    private double lateFeePerHour;

}
