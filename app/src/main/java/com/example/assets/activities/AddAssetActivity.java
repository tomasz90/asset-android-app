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

import org.json.JSONException;
import org.json.JSONObject;

public class AddAssetActivity extends AppCompatActivity {

    String assetSymbol;
    String rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        assetSymbol = getIntent().getStringExtra("asset");
        rates = getIntent().getStringExtra("rates");
        setAssetSymbol(assetSymbol);

        TextView valueLabel = findViewById(R.id.calculated_value);
        valueLabel.setText(assetSymbol + "/USD = " + String.format("%.2f", getRate()));
        Button save = findViewById(R.id.fab);

        EditText editText = findViewById(R.id.amount_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setBackgroundColor(getColor(R.color.magenta));
                save.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String textLabel;
                float value = 0f;
                if (s.toString().isEmpty()) {
                    save.setBackgroundColor(getColor(R.color.greyed_magenta));
                    save.setEnabled(false);
                } else {
                    value = Float.parseFloat(s.toString()) * getRate();
                }
                textLabel = getString(R.string.calculated_value, value);
                valueLabel.setText(textLabel);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAssetSymbol(String assetSymbol) {
        TextView tv = findViewById(R.id.asset_id);
        tv.setText(assetSymbol);
    }

    private float getRate() {
        JSONObject object = null;
        String rate = "";
        try {
            object = new JSONObject(rates);
             rate = object.getString(assetSymbol);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 1/Float.parseFloat(rate);
    }
}
