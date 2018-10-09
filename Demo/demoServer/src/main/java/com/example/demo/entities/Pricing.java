package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tbl_pricing")
public class Pricing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "from_hour", nullable = false)
    private int fromHour;

    @Basic(optional = false)
    @NotNull
    @Column(name = "price_per_hour", nullable = false)
    private double pricePerHour;
    @Column(name = "late_fee_per_hour")
    private Integer lateFeePerHour;
    @JoinColumn(name = "tbl_policy_has_tbl_vehicle_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PolicyHasTblVehicleType policyHasTblVehicleTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Integer getLateFeePerHour() {
        return lateFeePerHour;
    }

    public void setLateFeePerHour(Integer lateFeePerHour) {
        this.lateFeePerHour = lateFeePerHour;
    }

    public PolicyHasTblVehicleType getPolicyHasTblVehicleTypeId() {
        return policyHasTblVehicleTypeId;
    }

    public void setPolicyHasTblVehicleTypeId(PolicyHasTblVehicleType policyHasTblVehicleTypeId) {
        this.policyHasTblVehicleTypeId = policyHasTblVehicleTypeId;
    }
}
