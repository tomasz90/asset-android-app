package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

import java.util.Objects;

public class AddAssetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_assets_activity_title);

        setAssetSymbol(getIntent().getStringExtra("asset"));

        Button save = findViewById(R.id.fab);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
                startActivity(intent);
            }
        });

        EditText input = findViewById(R.id.amount_input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setBackgroundColor(getColor(R.color.magenta));
                save.setClickable(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    save.setBackgroundColor(getColor(R.color.greyed_magenta));
                    save.setClickable(false);
                }
            }
        });
    }

    private void setAssetSymbol(String assetSymbol) {
        TextView tv = findViewById(R.id.asset_id);
        tv.setText(assetSymbol);
    }
}
