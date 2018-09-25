package day01.swomfire.meterapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import Util.RmaAPIUtils;
import model.Meter;
import model.Transaction;
import model.User;
import model.VehicleType;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    Context context = this;
    User user;
    Meter meter;
    //User Info
    TextView txtName, txtBalance;
    //Meter Info
    TextView txtLocation, txtVehicleType, txtPrice, txtNumberOfHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtName = findViewById(R.id.txtName);
        txtBalance = findViewById(R.id.txtBalance);

        txtLocation = findViewById(R.id.txtLocation);
        txtVehicleType = findViewById(R.id.txtVehicleType);
        txtPrice = findViewById(R.id.txtPrice);
        txtNumberOfHour = findViewById(R.id.txtNumberOfHour);

        getUserInfo(getIntent().getStringExtra("userId"));
    }


    public void getUserInfo(String userId) {
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getUserById(Integer.parseInt(userId)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        bindUserInfo(user);
                        bindMeterInfo(MainActivity.meter);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Failed to Connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bindUserInfo(User user) {
        this.user = user;
        txtName.setText(user.getLastName() + " " + user.getFirstName());
        txtBalance.setText(user.getMoney());
    }

    public void bindMeterInfo(Meter meter) {
        if (meter != null) {
            this.meter = meter;
            txtLocation.setText(meter.getLocation());
            txtPrice.setText(meter.getPrice() + "");
            List<VehicleType> vehicleTypes = meter.getVehicleTypeList();
            if (vehicleTypes != null) {
                for (int i = 0; i < vehicleTypes.size(); i++) {
                    String vehicleType = (i > 0) ? ", " + vehicleTypes.get(i).getName() : vehicleTypes.get(i).getName();
                    txtVehicleType.setText(txtVehicleType.getText() + vehicleType);
                }
            }
        }
    }

    public void switchToMain(View view) {
        finish();
    }

    public void confirmPayment(View view) {
        Transaction transaction = new Transaction();
        transaction.setDateCheckIn(new Timestamp(System.currentTimeMillis()).getTime());
        transaction.setDateEnded(new Timestamp(System.currentTimeMillis()).getTime());
        transaction.setMeter(meter);
        transaction.setUser(user);
        transaction.setPrice(meter.getPrice());

        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.sendTransactionToServer(transaction).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful()) {
                    Transaction result = response.body();
                    Toast.makeText(context, result.getTransactionStatus().getName(), Toast.LENGTH_LONG).show();
                    changeToCompletePaymentView(result);
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void changeToCompletePaymentView(Transaction transaction) {
        setContentView(R.layout.activity_payment_complete);
        txtName = findViewById(R.id.txtName);
        txtBalance = findViewById(R.id.txtBalance);

        txtLocation = findViewById(R.id.txtLocation);
        txtVehicleType = findViewById(R.id.txtVehicleType);
        txtPrice = findViewById(R.id.txtPrice);
//        txtNumberOfHour = findViewById(R.id.txtNumberOfHour);
        TextView txtCheckInDate = findViewById(R.id.txtCheckInDate);
        TextView txtEndedDate = findViewById(R.id.txtEndedDate);

        String name = transaction.getUser().getLastName() + " " + transaction.getUser().getFirstName();
        txtName.setText(name);
        txtBalance.setText(transaction.getUser().getMoney());

        txtLocation.setText(transaction.getMeter().getLocation());
        txtVehicleType.setText(transaction.getMeter().getAllVehicleType());
        txtPrice.setText(transaction.getPrice()+"");

        txtCheckInDate.setText(new Timestamp(transaction.getDateCheckIn()).toString());
        txtEndedDate.setText(new Timestamp(transaction.getDateEnded()).toString());
    }
}
