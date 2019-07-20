package service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import Util.RmaAPIUtils;
import model.Order;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmsReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if (cursor.moveToFirst()) { // must check the result to prevent exception
                String address = cursor.getColumnName(2) + ":" + cursor.getString(2);
                String read = cursor.getColumnName(7) + ":" + cursor.getString(7);
                String body = cursor.getString(12);
                // use msgData
                System.out.println(address);
                if (body.equals("DK SMS")) {
                    updateUserSMS(cursor.getString(2), true);
                } else if (body.equals("HDK SMS")) {
                    updateUserSMS(cursor.getString(2), false);
                }
        } else {
            // empty box, no SMS
        }
    }
    private static Activity activity;

    public static void setActivity(Activity activity) {
        SmsReceiver.activity = activity;
    }

    public void updateUserSMS(String phone, boolean smsNoti) {
        User user = new User();
        user.setPhone(phone);
        user.setSmsNoti(smsNoti);
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.updateUserSmS(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User result = response.body();
//                    Toast.makeText(context, result.getTransactionStatus().getName(), Toast.LENGTH_LONG).show();
//                    changeToCompletePaymentView(result);
                    UserService userService = new UserService();
                    String msg = (result.getSmsNoti())? "Đăng ký nhận thông báo bằng sms thành công": "Hủy nhận thông báo bằng sms thành công";
                    userService.sendSMSMessage(result.getPhone(),msg,activity);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
