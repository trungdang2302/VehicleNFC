package day01.swomfire.meterapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Util.RmaAPIUtils;
import model.Meter;
import model.User;
import model.VehicleType;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Meter meter;

    Context context = this;
    TextView txtLocation, txtPrice, txtStatus, txtVehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        txtStatus = findViewById(R.id.txtStatus);
        txtVehicleType = findViewById(R.id.txtVehicleType);

        getMeterInfo();
    }

    public void getMeterInfo() {
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getMeterById(1).enqueue(new Callback<Meter>() {
            @Override
            public void onResponse(Call<Meter> call, Response<Meter> response) {
                if (response.isSuccessful()) {
                    Meter meterReceive = response.body();
                    meter = meterReceive;
                    bindMeterInfo(meter);
                }
            }

            @Override
            public void onFailure(Call<Meter> call, Throwable t) {
                Toast.makeText(context, "Failed to Connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bindMeterInfo(Meter meter) {
        if (meter != null) {
            txtLocation.setText(meter.getLocation());
            txtPrice.setText(meter.getPrice()+"");
            txtStatus.setText(meter.getMeterStatus().getName());
            txtVehicleType.setText(meter.getAllVehicleType());
        }
    }

    public void switchToScanDevice(View view) {
        Intent intent = new Intent(this, DeviceScanActivity.class);
        startActivity(intent);
    }
}
