package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbl_transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_check_in")
    @Temporal(TemporalType.DATE)
    private Date dataCheckIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_check_out")
    @Temporal(TemporalType.DATE)
    private Date dateCheckOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_ended")
    @Temporal(TemporalType.DATE)
    private Date dateEnded;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_check_in")
    private long dateCheckIn;
    @JoinColumn(name = "tbl_meter_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Meter meterId;
    @JoinColumn(name = "tbl_transaction_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransactionStatus transactionStatusId;
    @JoinColumn(name = "tbl_usr_id", referencedColumnName = "id")
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

    public Date getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(Date dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public Date getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(Date dateEnded) {
        this.dateEnded = dateEnded;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(long dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
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
