package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Util.RmaAPIUtils;
import adapter.OrderPricingAdapter;
import model.HourHasPrice;
import model.NotificationSerial;
import model.Order;
import model.OrderPricing;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NFCActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback {
    Context context;
    String token = null;

    Chronometer parkingChorno;
    boolean blink = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        NotificationSerial notificationSerial = (NotificationSerial) getIntent().getSerializableExtra("Notification");
        if (notificationSerial != null) {
            setContentView(R.layout.activity_checkout);
            parkingChorno = findViewById(R.id.chroParking);

            getOrderInfo(notificationSerial.getOrderId());


        }

        context = this;
        token = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);


        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);
    }

    /**
     * Ndef Record that will be sent over via NFC
     *
     * @param nfcEvent
     * @return
     */
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = "{'userId':2,'token':'" + token + "'}";
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }


    public void popUpMenu(View view) {
        Intent intent = new Intent(NFCActivity.this, PopMenuActivity.class);
        startActivity(intent);
    }

    public void updatePrice(int h, int m, Order order) {
        //TODO update price after every chrono tick.
        List<OrderPricing> orderPricings = order.getOrderPricings();
        lblTotal = findViewById(R.id.lblTotal);
        double totalPrice = 0;
        List<HourHasPrice> hourHasPrices = new ArrayList<>();
        while (h > 0) {
            hourHasPrices.add(new HourHasPrice(h, null));
            h--;
        }

        double lastPrice = 0;
        for (OrderPricing orderPricing : orderPricings
                ) {
            if (orderPricing.getPricePerHour() > lastPrice) {
                lastPrice = orderPricing.getPricePerHour();
            }
            for (HourHasPrice hourHasPrice : hourHasPrices) {
                if (orderPricing.getFromHour() < hourHasPrice.getHour()) {
                    hourHasPrice.setPrice(orderPricing.getPricePerHour());
                }
            }
        }

        for (
                HourHasPrice hourHasPrice : hourHasPrices
                ) {
            totalPrice += hourHasPrice.getPrice();
        }

        totalPrice += lastPrice * ((double) m/ 60);
        lblTotal.setText((long)(totalPrice*1000)+" đ");
    }

    TextView lblTotal;

    private void setUpChrono(Order order) {
        System.out.println(new Date().getTime());
        parkingChorno.setBase(order.getCheckInDate());
        parkingChorno.setOnChronometerTickListener(chronometer -> {
            long time = new Date().getTime() - chronometer.getBase();
            int h = (int) (time / 3600000);
            int m = (int) (time - h * 3600000) / 60000;
            String timeString = (blink) ? String.format("%02d %02d", h, m) : String.format("%02d:%02d", h, m);
            blink = !blink;
            chronometer.setText(timeString);
            updatePrice(h,m,order);
        });
        parkingChorno.start();
    }

    public void getOrderInfo(String orderId) {
        if (orderId == null) {
            return;
        }
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getOrderById(Integer.parseInt(orderId)).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Order result = response.body();
                    if (result != null) {
                        setUpChrono(result);
                        setUpOrderInfo(result);
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void setUpOrderInfo(Order order) {
        TextView lblLocation, lblVehicleType, lblVehicleNumber, lblTotal;
        lblLocation = findViewById(R.id.lblOrderLocation);
        lblVehicleNumber = findViewById(R.id.lblUserVehicleNumber);
        lblVehicleType = findViewById(R.id.lblUserVehicleType);

        lblLocation.setText(order.getLocation().getLocation());

        lblVehicleNumber.setText(order.getUser().getVehicleNumber());
        lblVehicleType.setText(order.getUser().getVehicleType().getName());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOrderPricing);
        OrderPricingAdapter orderPricingAdapter = new OrderPricingAdapter(order.getOrderPricings());
        GridLayoutManager gLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderPricingAdapter);
    }
}
