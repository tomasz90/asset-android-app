package com.example.assets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

import java.util.Objects;

public class DoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        int TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }, TIME_OUT);
    }
}
