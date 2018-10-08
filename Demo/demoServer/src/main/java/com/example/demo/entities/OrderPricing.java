package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tbl_order_pricing")
public class OrderPricing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "from_hour", nullable = false)
    private int fromHour;
    @Column(name = "to_hour")
    private Integer toHour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price_per_hour", nullable = false)
    private double pricePerHour;
    @Column(name = "late_fee_per_hour")
    private Integer lateFeePerHour;
    @JoinColumn(name = "tbl_order_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Order OrderId;
    public OrderPricing() {
    }

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

    public Integer getToHour() {
        return toHour;
    }

    public void setToHour(Integer toHour) {
        this.toHour = toHour;
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

    public Order getOrderId() {
        return OrderId;
    }

    public void setOrderId(Order orderId) {
        OrderId = orderId;
    }
}
