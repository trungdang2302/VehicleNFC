package service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Util.RmaAPIUtils;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {


    public static void updateUserSMS(String phone, boolean smsNoti) {
        User user = new User();
        user.setPhone(phone);
        user.setSmsNoti(smsNoti);
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.updateUserSmS(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
//                    Transaction result = response.body();
//                    Toast.makeText(context, result.getTransactionStatus().getName(), Toast.LENGTH_LONG).show();
//                    changeToCompletePaymentView(result);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    public void sendSMSMessage(String phoneNo, String message, Activity activity) {

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
        }
    }

    public static String convertMoney(double money) {
        String base = (long) money * 1000 + "";
        String[] strings = base.split("");
        String result = "";
        int count = 0;
        for (int i = strings.length - 1; i > 0; i--) {
            count++;
            result = strings[i] + result;
            if (count == 3) {
                result = "." + result;
                count = 0;
            }
        }
        result = result + " vnđ";
        return result;
    }
}
