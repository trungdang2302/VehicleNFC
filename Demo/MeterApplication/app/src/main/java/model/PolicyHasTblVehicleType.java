package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PolicyHasTblVehicleType {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("policyId")
    @Expose
    private int policyId;

    @SerializedName("vehicleTypeId")
    @Expose
    private VehicleType vehicleType;


    @SerializedName("pricings")
    @Expose
    private List<Pricing> pricings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Pricing> getPricings() {
        return pricings;
    }

    public void setPricings(List<Pricing> pricings) {
        this.pricings = pricings;
    }
}
