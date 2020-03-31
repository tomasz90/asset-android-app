package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;
import com.example.assets.constants.IntentExtra;
import com.example.assets.util.StorageManager;


public class AddAssetActivity extends AppCompatActivity {

    String rate;
    String assetSymbol;
    String calculatedValueText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        rate = getIntent().getStringExtra(IntentExtra.RATE);
        assetSymbol = getIntent().getStringExtra(IntentExtra.ASSET);

        TextView symbolTextView = findViewById(R.id.asset_symbol);
        symbolTextView.setText(assetSymbol);

        TextView calculatedValueTextView = findViewById(R.id.calculated_value);
        calculatedValueText = getString(R.string.rate, assetSymbol, getFloatRate());
        calculatedValueTextView.setText(calculatedValueText);

        Button saveButton = findViewById(R.id.fab);
        saveButton.setOnClickListener(view -> {

            StorageManager manager = new StorageManager(this);
            manager.addEntry(assetSymbol, editText.getText().toString());
            //manager.deleteFile();

            Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
            startActivity(intent);
        });

        ImageView closeButton = findViewById(R.id.xbutton);
        closeButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddAssetActivity.this, MainActivity.class);
            startActivity(intent);
        });

        editText = findViewById(R.id.amount_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setBackgroundColor(getColor(R.color.magenta));
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                float value = 0f;
                if (s.toString().isEmpty()) {
                    saveButton.setBackgroundColor(getColor(R.color.greyed_magenta));
                    saveButton.setEnabled(false);
                } else {
                    value = Float.parseFloat(s.toString()) * getFloatRate();
                }
                calculatedValueText = getString(R.string.calculated_value, value);
                calculatedValueTextView.setText(calculatedValueText);
            }
        });
    }

    private float getFloatRate() {
        return 1 / Float.parseFloat(rate);
    }
}
