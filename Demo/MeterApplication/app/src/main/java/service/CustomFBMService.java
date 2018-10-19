package service;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import day01.swomfire.meterapplication.DeviceScanActivity;

/**
 * Created by elpsychris on 16/03/2018.
 */

public class CustomFBMService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    private static Activity activity;

    public static void setActivity(Activity activity) {
        CustomFBMService.activity = activity;
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        UserService userService = new UserService();
        String phoneNumber = remoteMessage.getData().get("toPhoneNumber");
        String msg = remoteMessage.getData().get("body");
        userService.sendSMSMessage(phoneNumber, msg, activity);
    }


    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        System.out.println(token);
    }
}
