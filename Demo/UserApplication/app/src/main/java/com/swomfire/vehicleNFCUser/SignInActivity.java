package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Util.RmaAPIUtils;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends Activity {
    Context context;
    EditText txtPhone,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        context = this;
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void signIn(View view) {
//        RmaAPIService mService = RmaAPIUtils.getAPIService();
//        String phone = txtPhone.getText().toString();
//        String password = txtPassword.getText().toString();
//        mService.login(phone,password).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    User result = response.body();
////                    Toast.makeText(context, result.getVehicleNumber(), Toast.LENGTH_LONG).show();
////                    changeToCompletePaymentView(result);
//                    if (result!=null) {
//                        Intent intent = new Intent(context, NFCActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//
        Intent intent = new Intent(this, NFCActivity.class);
        startActivity(intent);

    }

    public void toSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
