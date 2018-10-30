package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class VehicleType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Size(max = 255)
    @Column(name = "brand")
    private String brand;
    @Size(max = 255)
    @Column(name = "expire_date")
    private String expireDate;
    @Size(max = 255)
    @Column(name = "license_plate_id")
    private String licensePlateId;
    @Size(max = 255)
    @Column(name = "size")
    private String size;
    @Size(max = 255)
    @Column(name = "verify_date")
    private String verifyDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tbl_vehicle_type_id")
    private int vehicleTypeId;
    @ManyToMany(mappedBy = "vehicleTypeList")
    private List<Policy> policyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleTypeId")
    private List<Order> orderList;
    @OneToMany(mappedBy = "tblVehicleTypeId")
    private List<Vehicle> vehicleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleTypeId")
    private List<PolicyInstanceHasTblVehicleType> tblPolicyInstanceHasTblVehicleTypeList;

    public VehicleType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getLicensePlateId() {
        return licensePlateId;
    }

    public void setLicensePlateId(String licensePlateId) {
        this.licensePlateId = licensePlateId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public List<Policy> getPolicyList() {
        return policyList;
    }

    public void setPolicyList(List<Policy> policyList) {
        this.policyList = policyList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<PolicyInstanceHasTblVehicleType> getTblPolicyInstanceHasTblVehicleTypeList() {
        return tblPolicyInstanceHasTblVehicleTypeList;
    }

    public void setTblPolicyInstanceHasTblVehicleTypeList(List<PolicyInstanceHasTblVehicleType> tblPolicyInstanceHasTblVehicleTypeList) {
        this.tblPolicyInstanceHasTblVehicleTypeList = tblPolicyInstanceHasTblVehicleTypeList;
    }
}
