package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Swomfire on 23-Sep-18.
 */

public class User {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("money")
    @Expose
    private String money;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("vehicleNumber")
    @Expose
    private String vehicleNumber;

    @SerializedName("licensePlateId")
    @Expose
    private String licensePlateId;

    @SerializedName("vehicleTypeId")
    @Expose
    private VehicleType vehicleType;

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getMoney() {
        return money;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLicensePlateId() {
        return licensePlateId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
