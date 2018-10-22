package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

import Util.RmaAPIUtils;
import adapter.OrderPricingAdapter;
import model.Order;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPopupPrice extends Activity {

    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_popup_price);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        int id = (int) getIntent().getExtras().get("itemID");

        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getOrderById(id).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Order result = response.body();
                    if (result != null) {

                        txtTime = findViewById(R.id.txtTime);

                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.US);

                        txtTime.setText(sdf.format(result.getAllowedParkingFrom()) + " đến " + sdf.format(result.getAllowedParkingTo()));

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyMain);
                        OrderPricingAdapter orderPricingAdapter = new OrderPricingAdapter(result.getOrderPricings());
                        GridLayoutManager gLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        recyclerView.setLayoutManager(gLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(orderPricingAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        getWindow().setLayout((int) (width * 0.73), (int) (height * 0.44));
    }
}
