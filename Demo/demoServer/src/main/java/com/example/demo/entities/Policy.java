package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_policy")
public class Policy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "allowed_parking_from")
    private long allowedParkingFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "allowed_parking_to")
    private long allowedParkingTo;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "policyId")
//    private List<PolicyHasTblVehicleType> policyHasTblVehicleTypeList;

    public Policy() {
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

//    public List<PolicyHasTblVehicleType> getPolicyHasTblVehicleTypeList() {
//        return policyHasTblVehicleTypeList;
//    }
//
//    public void setPolicyHasTblVehicleTypeList(List<PolicyHasTblVehicleType> policyHasTblVehicleTypeList) {
//        this.policyHasTblVehicleTypeList = policyHasTblVehicleTypeList;
//    }
}
