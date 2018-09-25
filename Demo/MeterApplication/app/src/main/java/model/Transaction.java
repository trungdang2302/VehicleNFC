package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by Swomfire on 24-Sep-18.
 */

public class Transaction {

    @SerializedName("dataCheckIn")
    @Expose
    private long dateCheckIn;

    @SerializedName("dateEnded")
    @Expose
    private long dateEnded;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("meterId")
    @Expose
    private Meter meter;

    @SerializedName("userId")
    @Expose
    private User user;

    @SerializedName("transactionStatusId")
    @Expose
    private TransactionStatus transactionStatus;

    public long getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(long dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public long getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(long dateEnded) {
        this.dateEnded = dateEnded;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
