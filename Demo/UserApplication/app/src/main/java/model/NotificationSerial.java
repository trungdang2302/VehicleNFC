package model;


import android.app.Notification;

import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;
import java.util.Map;

public class NotificationSerial implements Serializable {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static NotificationSerial convertNotiToSerial(Map<String, String> notification) {
        NotificationSerial notificationSerial = new NotificationSerial();
        notificationSerial.setOrderId(notification.get("orderId"));
        return notificationSerial;
    }

    public static NotificationSerial convertNotiToSerial(RemoteMessage.Notification notification) {
        NotificationSerial notificationSerial = new NotificationSerial();
//        notificationSerial.setOrderId(notification.get("orderId"));
        return notificationSerial;
    }
}
