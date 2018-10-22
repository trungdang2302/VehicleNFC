package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

public class PopMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_menu);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setGravity(Gravity.LEFT);
        getWindow().setLayout((int) (width * .8), height);
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
}
