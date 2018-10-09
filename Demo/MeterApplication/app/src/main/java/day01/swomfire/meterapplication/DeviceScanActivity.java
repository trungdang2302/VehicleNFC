package day01.swomfire.meterapplication;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import Util.RmaAPIUtils;
import model.Location;
import model.Order;
import model.Transaction;
import model.User;
import remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceScanActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    Tag myTag;
    Context context;
    PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan);
        context = this;
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "Thiết bị không hỗ trợ NFC.", Toast.LENGTH_LONG).show();
            finish();
        }

        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                buildTagViews(msgs);
            } else {
                Toast.makeText(context, "Thiết bị không hợp lệ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"

        try {
            // Get the Text
            text = new String(payload, 0, payload.length, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        } catch (StringIndexOutOfBoundsException e) {
            text = new String(payload);
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra("userId",text);
//        startActivity(intent);
//        finish();
        try {
            JSONObject jsonObj = new JSONObject(text);
            confirmPayment(jsonObj.getString("userId"),jsonObj.getString("token"));

        }catch (JSONException e){
            //TODO change
            Toast.makeText(context, "ha ha good luck", Toast.LENGTH_SHORT).show();
        }

    }

    public void confirmPayment(String userId, String token) {
        Order order = new Order();

        User user = new User();
        user.setId(userId);
        user.setDeviceToken(token);

        Location location = new Location();
        location.setId("1");

        order.setUser(user);

        order.setLocation(location);

        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.sendTransactionToServer(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
//                    Transaction result = response.body();
//                    Toast.makeText(context, result.getTransactionStatus().getName(), Toast.LENGTH_LONG).show();
//                    changeToCompletePaymentView(result);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
             Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    public void switchToMain(View view) {
        finish();
    }
}
