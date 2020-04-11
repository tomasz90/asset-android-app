package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

public class DoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        int TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            Intent i = new Intent(DoneActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }, TIME_OUT);
    }
}
