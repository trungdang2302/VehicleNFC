package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
}
