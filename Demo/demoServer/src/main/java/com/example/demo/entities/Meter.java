package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_meter")
public class Meter implements Serializable {

    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;
    @Column(name = "price")
    private Integer price;
    @JoinTable(name = "tbl_meter_has_tbl_vehicle_type", joinColumns = {
            @JoinColumn(name = "tbl_meter_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "tbl_vehicle_type_id", referencedColumnName = "id")})
    @ManyToMany
    private List<VehicleType> vehicleTypeList;
    @JoinColumn(name = "tbl_meter_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MeterStatus meterStatusId;

    public Meter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public MeterStatus getMeterStatusId() {
        return meterStatusId;
    }

    public void setMeterStatusId(MeterStatus meterStatusId) {
        this.meterStatusId = meterStatusId;
    }
}
