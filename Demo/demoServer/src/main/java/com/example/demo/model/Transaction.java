/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author BaoNQ
 */
@Entity
@Table(name = "tbl_transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)

    @Column(name = "date_check_in")
    private Long dataCheckIn;
    @Basic(optional = false)

    @Column(name = "date_check_out")
    private Long dateCheckOut;
    @Basic(optional = false)

    @Column(name = "date_ended")
    private Long dateEnded;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;

    @JoinColumn(name = "tbl_meter_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Meter meterId;

    @JoinColumn(name = "tbl_transaction_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransactionStatus transactionStatusId;

    @JoinColumn(name = "tbl_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(Long dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Long getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Long dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public Long getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(Long dateEnded) {
        this.dateEnded = dateEnded;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Meter getMeterId() {
        return meterId;
    }

    public void setMeterId(Meter meterId) {
        this.meterId = meterId;
    }

    public TransactionStatus getTransactionStatusId() {
        return transactionStatusId;
    }

    public void setTransactionStatusId(TransactionStatus transactionStatusId) {
        this.transactionStatusId = transactionStatusId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
