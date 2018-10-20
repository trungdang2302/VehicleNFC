package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Util.RmaAPIUtils;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.UserService;

public class ProfileActivity extends Activity {

    TextView txtMoney, txtName, txtPhone, txtVehicalID, txtVehicalName, txtDangKiem;
    ImageView imageXe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtMoney = findViewById(R.id.txtMoney);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtVehicalID = findViewById(R.id.txtVehicalID);
        txtVehicalName = findViewById(R.id.txtVehicalName);
        txtDangKiem = findViewById(R.id.txtDangKiem);
        imageXe = findViewById(R.id.imageXe);

        SharedPreferences prefs = getSharedPreferences("localData", MODE_PRIVATE);
        String restoredText = prefs.getString("phoneNumberSignIn", null);
        String name = "1";
        if (restoredText != null) {
            name = prefs.getString("phoneNumberSignIn", "1");
            //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        }


        //String name = "1324658";

        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getUserByPhone(name).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {

                        txtMoney.setText(UserService.convertMoney(Double.parseDouble(user.getMoney())));
                        txtName.setText(user.getFirstName() + " " + user.getLastName());
                        txtPhone.setText(user.getPhone());
                        txtVehicalID.setText(user.getVehicleNumber());

                        String count = user.getVehicleType().getName();
                        txtVehicalName.setText(count);
                        if (count.matches("16 chỗ")) {
                            imageXe.setImageDrawable(getResources().getDrawable(R.drawable.cartypeicobig));
                        }

                        txtDangKiem.setText(user.getLicensePlateId());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
