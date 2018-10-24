package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import service.DBHelper;

public class PopMenuActivity extends Activity {

    TextView txtName, txtPhoneNumber;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.popup_menu_enter, R.anim.popup_menu_exit);
        setContentView(R.layout.activity_pop_menu);
        context = this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setGravity(Gravity.LEFT);
        getWindow().setLayout((int) (width * .8), height);

        txtName = findViewById(R.id.txtUserName);
        txtPhoneNumber = findViewById(R.id.txtUserPhone);

        SharedPreferences prefs = getSharedPreferences("localData", MODE_PRIVATE);
        String userName = prefs.getString("userName", "1");
        String userPhoneNumber = prefs.getString("phoneNumberSignIn", "1");
        txtName.setText(userName);
        txtPhoneNumber.setText(userPhoneNumber);

//        getWindow().setLayout(width, height);
    }

    public void topUp(View view) {
        Intent intent = new Intent(this, ActivityTopUpExtras.class);
        startActivity(intent);
    }

    public void closePopup(View view) {
        finish();
    }

    public void toUserProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void viewHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.popup_menu_enter, R.anim.popup_menu_exit);
    }

    public void logOut(View view) {
        DBHelper db = new DBHelper(context);
        //TODO clear all records
        db.deleteAllContact();
        //Clear old id
        SharedPreferences.Editor a = getSharedPreferences("localData", MODE_PRIVATE).edit();
        a.clear();

        Intent intent = new Intent(this, WelcomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
