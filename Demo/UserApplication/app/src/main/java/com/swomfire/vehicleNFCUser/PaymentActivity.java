package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import Util.RmaAPIUtils;
import model.ProductObj;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends Activity {

    private double amount = 25000;

    ProductObj item = new ProductObj("starter pack", amount, "USD");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            PaymentConfirmation confirm = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {

                    Log.i("Payment Example", confirm.toJSONObject().toString(4));
                    topUpAccount("1",amount);
                    Toast.makeText(this, "Payment successfull!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Log.e("Payment Example", "Error:", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "Canceled");
        } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "Valid payment");
        }
    }

    public void topUpAccount(String userId, double amount){

        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.topUp(userId,amount).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
//                    Order result = response.body();
//                    if (result != null) {
//                        setUpChrono(result);
//                        setUpOrderInfo(result);
//                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}