package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class PolicyInstance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "allowed_parking_from", nullable = false)
    private long allowedParkingFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "allowed_parking_to", nullable = false)
    private long allowedParkingTo;
    @ManyToMany(mappedBy = "tblPolicyInstanceList")
    private List<Location> locationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblPolicyInstanceId")
    private List<PolicyInstanceHasTblVehicleType> policyInstanceHasTblVehicleTypeList;

    public PolicyInstance() {
    }

    public PolicyInstance(@NotNull long allowedParkingFrom, @NotNull long allowedParkingTo, List<Location> locationList, List<PolicyInstanceHasTblVehicleType> policyInstanceHasTblVehicleTypeList) {
        this.allowedParkingFrom = allowedParkingFrom;
        this.allowedParkingTo = allowedParkingTo;
        this.locationList = locationList;
        this.policyInstanceHasTblVehicleTypeList = policyInstanceHasTblVehicleTypeList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<PolicyInstanceHasTblVehicleType> getPolicyInstanceHasTblVehicleTypeList() {
        return policyInstanceHasTblVehicleTypeList;
    }

    public void setPolicyInstanceHasTblVehicleTypeList(List<PolicyInstanceHasTblVehicleType> policyInstanceHasTblVehicleTypeList) {
        this.policyInstanceHasTblVehicleTypeList = policyInstanceHasTblVehicleTypeList;
    }
}
