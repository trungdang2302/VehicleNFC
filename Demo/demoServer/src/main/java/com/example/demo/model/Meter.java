/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author BaoNQ
 */
@Entity
@Table(name = "tbl_meter")
public class Meter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public MeterStatus getMeterStatusId() {
        return meterStatusId;
    }

    public List<VehicleType> getVehicleTypeList(){
        return  vehicleTypeList;
    }
}
