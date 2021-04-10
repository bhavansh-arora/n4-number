package com.sugarbaby.luckywheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class new_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        SharedPreferences sharedpreferences = getSharedPreferences("preference", MODE_PRIVATE);
        if( sharedpreferences.getBoolean("logged", false)){

            Intent intent = new Intent(new_login.this,profile.class);
            startActivity(intent);
            finish();
        }
    }

    public void mobile_login(View view) {
        Intent intent = new Intent(new_login.this,mobile_number_authentication.class);
        startActivity(intent);
        finish();
    }
}