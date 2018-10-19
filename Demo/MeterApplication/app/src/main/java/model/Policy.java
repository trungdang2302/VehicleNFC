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
    private long allowedParkingTo;

    @SerializedName("policyHasTblVehicleTypeList")
    @Expose
    private List<PolicyHasTblVehicleType> policyHasPricings;


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

    public long getAllowedParkingTo() {
        return allowedParkingTo;
    }

    public void setAllowedParkingTo(long allowedParkingTo) {
        this.allowedParkingTo = allowedParkingTo;
    }

    public List<PolicyHasTblVehicleType> getPolicyHasPricings() {
        return policyHasPricings;
    }

    public void setPolicyHasPricings(List<PolicyHasTblVehicleType> policyHasPricings) {
        this.policyHasPricings = policyHasPricings;
    }
}
