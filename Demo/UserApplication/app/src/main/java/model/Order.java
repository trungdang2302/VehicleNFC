package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Swomfire on 24-Sep-18.
 */

public class Order {

    @SerializedName("dataCheckIn")
    @Expose
    private long dateCheckIn;

    @SerializedName("dateEnded")
    @Expose
    private long dateEnded;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("locationId")
    @Expose
    private Location meter;

    @SerializedName("userId")
    @Expose
    private User user;

    @SerializedName("orderStatusId")
    @Expose
    private OrderStatus orderStatus;

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

    public Location getMeter() {
        return meter;
    }

    public void setMeter(Location meter) {
        this.meter = meter;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
