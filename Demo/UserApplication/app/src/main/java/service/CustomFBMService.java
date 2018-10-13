package service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.swomfire.vehicleNFCUser.NFCActivity;
import com.swomfire.vehicleNFCUser.R;

import model.NotificationSerial;

/**
 * Created by elpsychris on 16/03/2018.
 */

public class CustomFBMService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

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
        Intent intent = new Intent(getApplicationContext(), NFCActivity.class);
        intent.putExtra("Notification", NotificationSerial.convertNotiToSerial(remoteMessage.getData()));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        builder.setContentTitle(remoteMessage.getData().get("title"));
        builder.setContentText(remoteMessage.getData().get("body"));
        builder.setSmallIcon(R.drawable.logo);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

    }


    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        System.out.println(token);
    }
}
