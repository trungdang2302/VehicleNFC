package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import Util.RmaAPIUtils;
import model.ProductObj;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.UserService;

public class ActivityTopUpExtras extends Activity {

    ImageView imgPlus, imgMinus;

    TextView txtMoney, txtMoneyShow, txtConvert, txtConvertShow;

    boolean isFromProfile = false;
    boolean clickPay = false;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_extras);
        isFromProfile = getIntent().getBooleanExtra("isFromProfile", false);
        context = this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .95), (int) (height * .6));
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        txtMoney = findViewById(R.id.txtMoney);
        txtMoneyShow = findViewById(R.id.txtMoneyShow);
        txtConvert = findViewById(R.id.txtConvert);
        txtConvertShow = findViewById(R.id.txtConvertShow);

        double money = Double.parseDouble(txtMoney.getText() + "");
        txtConvert.setText(UserService.convertVNDToUSD(money) + "");
        txtConvertShow.setText(UserService.convertMoneyUSD(UserService.convertVNDToUSD(money)));

        ((TextView) findViewById(R.id.lbl_toolbar)).setText("Nạp tiền bằng Paypal");
    }

    public void onClickMinus(View v) {

        String s = txtMoney.getText().toString();
        int money = 0;
        if (!s.matches("")) {
            money = Integer.parseInt(s);
        }

        if (money < 50) {
            money = 0;
        } else {
            money -= 50;
        }

        txtMoney.setText("" + money);
        txtMoneyShow.setText(UserService.convertMoney(money));
        txtConvert.setText(UserService.convertVNDToUSD(money) + "");
        txtConvertShow.setText(UserService.convertMoneyUSD(UserService.convertVNDToUSD(money)));
    }

    public void onClickPlus(View v) {

        String s = txtMoney.getText().toString();
        int money = 0;
        if (!s.matches("")) {
            money = Integer.parseInt(s);
        }

        money += 50;

        txtMoney.setText("" + money);
        txtMoneyShow.setText(UserService.convertMoney(money));
        txtConvert.setText(UserService.convertVNDToUSD(money) + "");
        txtConvertShow.setText(UserService.convertMoneyUSD(UserService.convertVNDToUSD(money)));
    }

    public void topUpConfirm(View view) {
//        Intent intent = new Intent(this, PaymentActivity.class);
//        startActivity(intent);
        amount = Double.parseDouble(txtMoney.getText() + "");
        ProductObj item = new ProductObj("Nạp tiền vào tài khoản", UserService.convertVNDToUSD(amount), "USD");
        onBuyPressed(item);
    }


    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId("ARZV5aC-CZnIsiEp4S8PVWcSDY0TbEQx0tCNZs_NHBuYdsFsctaPnvhNLtdAYQhw8Dzn6p7S0SUNpG9i");

    public void onBuyPressed(ProductObj obj) {

        PayPalPayment payment = new PayPalPayment(obj.getPrice(), obj.getCurrency(), obj.getName(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);

    }

    private double amount;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && data != null) {
            PaymentConfirmation confirm = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                SharedPreferences prefs = getSharedPreferences("localData", MODE_PRIVATE);
                String restoredText = prefs.getString("userId", null);
                if (restoredText != null) {
                    topUpAccount(restoredText, amount);
                } else {
                    finish();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "Canceled");
        } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "Valid payment");
        }
    }

    public void topUpAccount(String userId, double amount) {

        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.topUp(userId, amount).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    clickPay = true;
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        if (!isFromProfile && clickPay) {
            Intent intent = new Intent(context, ProfileActivity.class);
            startActivity(intent);
        }
    }
}

