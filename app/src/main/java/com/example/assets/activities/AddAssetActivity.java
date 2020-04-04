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
import com.example.assets.util.DataProvider;
import com.example.assets.util.StorageManager;


public class AddAssetActivity extends AppCompatActivity {

    String assetSymbol;
    EditText editText;
    TextView calculatedValueTextView;
    float value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        assetSymbol = getIntent().getStringExtra(IntentExtra.ASSET);

        TextView symbolTextView = findViewById(R.id.asset_symbol);
        symbolTextView.setText(assetSymbol);

        calculatedValueTextView = findViewById(R.id.calculated_value);

        DataProvider.execute(false, dataFromApi -> {
            float rate = 1 / Float.parseFloat(dataFromApi.getString(assetSymbol));
            String textToDisplay = getString(R.string.rate, assetSymbol, rate);
            calculatedValueTextView.setText(textToDisplay);
        });

        Button saveButton = findViewById(R.id.fab);
        saveButton.setOnClickListener(view -> {

            StorageManager manager = new StorageManager(this);
            manager.addEntry(assetSymbol, editText.getText().toString());

            Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
            startActivity(intent);
        });

        ImageView closeButton = findViewById(R.id.xbutton);
        closeButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddAssetActivity.this, MainActivity.class);
            AddAssetActivity.this.startActivity(intent);
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
                value = 0f;
                if (s.toString().isEmpty()) {
                    saveButton.setBackgroundColor(getColor(R.color.greyed_magenta));
                    saveButton.setEnabled(false);
                } else {
                    value = Float.parseFloat(s.toString());
                }
                DataProvider.execute(false, data -> {
                    float rate = 1 / Float.parseFloat(data.getString(assetSymbol));
                    String textToDisplay = getString(R.string.calculated_value, value * rate);
                    calculatedValueTextView.setText(textToDisplay);
                });
            }
        });
    }
}
