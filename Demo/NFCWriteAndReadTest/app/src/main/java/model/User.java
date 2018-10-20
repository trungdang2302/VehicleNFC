package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Swomfire on 23-Sep-18.
 */

public class User {

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("vehicleNumber")
    @Expose
    private String vehicleNumber;

    public String getPhone() {
        return phone;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}
