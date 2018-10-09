package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Policy {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("allowedParkingFrom")
    @Expose
    private long allowedParkingFrom;

    @SerializedName("allowedParkingTo")
    @Expose
    private int allowedParkingTo;

    @SerializedName("")
    @Expose
    private List<PolicyHasPricing> policyHasPricings;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAllowedParkingFrom() {
        return allowedParkingFrom;
    }

    public void setAllowedParkingFrom(long allowedParkingFrom) {
        this.allowedParkingFrom = allowedParkingFrom;
    }

    public int getAllowedParkingTo() {
        return allowedParkingTo;
    }

    public void setAllowedParkingTo(int allowedParkingTo) {
        this.allowedParkingTo = allowedParkingTo;
    }

    public List<PolicyHasPricing> getPolicyHasPricings() {
        return policyHasPricings;
    }

    public void setPolicyHasPricings(List<PolicyHasPricing> policyHasPricings) {
        this.policyHasPricings = policyHasPricings;
    }
}
