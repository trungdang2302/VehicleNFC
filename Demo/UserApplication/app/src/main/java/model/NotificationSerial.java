package model;

import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;

public class NotificationSerial implements Serializable {

    public static NotificationSerial convertNotiToSerial(RemoteMessage.Notification notification){
        return new NotificationSerial();
    }
}
