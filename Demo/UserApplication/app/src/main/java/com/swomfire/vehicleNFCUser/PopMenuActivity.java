package com.swomfire.vehicleNFCUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class PopMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_menu);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width= displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

//        getWindow().setLayout((int)(width*.8),(int) (height*.6));
        getWindow().setLayout(width, height);
    }

    public void payShit(View view) {
        Intent intent = new Intent(this,PaymentActivity.class);
        startActivity(intent);
    }
}
