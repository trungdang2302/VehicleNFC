package com.example.demo.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_policy_has_tbl_vehicle_type")
public class PolicyHasTblVehicleType implements Serializable {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "tbl_policy_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Policy policyId;
    @JoinColumn(name = "tbl_vehicle_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VehicleType vehicleTypeId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblPolicyHasTblVehicleTypeId")
    @Transient
    private List<Pricing> pricings;

    @NotNull
    @Column(name = "min_hour")
    private Integer minHour;

    public PolicyHasTblVehicleType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Policy getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Policy policyId) {
        this.policyId = policyId;
    }

    public VehicleType getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(VehicleType vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public List<Pricing> getPricings() {
        return pricings;
    }

    public void setPricings(List<Pricing> pricings) {
        this.pricings = pricings;
    }

    public Integer getMinHour() {
        return minHour;
    }

    public void setMinHour(Integer minHour) {
        this.minHour = minHour;
    }
}
