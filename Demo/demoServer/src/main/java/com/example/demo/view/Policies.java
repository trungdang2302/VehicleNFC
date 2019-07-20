package com.example.demo.view;

import java.io.Serializable;

public class Policies implements Serializable {
    private Integer id;
    private Long allowedParkingFrom;
    private Long allowedParkingTo;

    public Policies() {
    }

    public Policies(Integer id, Long allowedParkingFrom, Long allowedParkingTo) {
        this.id = id;
        this.allowedParkingFrom = allowedParkingFrom;
        this.allowedParkingTo = allowedParkingTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAllowedParkingFrom() {
        return allowedParkingFrom;
    }

    public void setAllowedParkingFrom(Long allowedParkingFrom) {
        this.allowedParkingFrom = allowedParkingFrom;
    }

    public Long getAllowedParkingTo() {
        return allowedParkingTo;
    }

    public void setAllowedParkingTo(Long allowedParkingTo) {
        this.allowedParkingTo = allowedParkingTo;
    }
}
