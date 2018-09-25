package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Swomfire on 23-Sep-18.
 */

public class Meter {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("vehicleTypeList")
    @Expose
    private List<VehicleType> vehicleTypeList;

    @SerializedName("meterStatusId")
    @Expose
    private MeterStatus meterStatus;

    public int getId() {
        return id;
    }

    public String getLocation() {
        String[] properties = location.split("_");
        return properties[0]+", "+properties[1];
    }

    public int getPrice() {
        return price;
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public MeterStatus getMeterStatus() {
        return meterStatus;
    }

    public String getAllVehicleType(){
        if (vehicleTypeList != null) {
            String vehicleType = "";
            for (int i = 0; i < vehicleTypeList.size(); i++) {
                 vehicleType += (i > 0) ? ", " + vehicleTypeList.get(i).getName() : vehicleTypeList.get(i).getName();
            }
            return vehicleType;
        }
        return "";
    }
}
