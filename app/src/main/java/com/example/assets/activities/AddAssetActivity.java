package com.example.assets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddAssetActivity extends AppCompatActivity {

    String assetSymbol;
    String rates;
    String calculatedValueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        rates = getIntent().getStringExtra("rates");
        assetSymbol = getIntent().getStringExtra("asset");

        TextView symbolTextView = findViewById(R.id.asset_id);
        symbolTextView.setText(assetSymbol);

        TextView calculatedValueTextView = findViewById(R.id.calculated_value);
        calculatedValueText = getString(R.string.rate, assetSymbol, getRate());
        calculatedValueTextView.setText(calculatedValueText);


        Button save = findViewById(R.id.fab);
        save.setOnClickListener(view -> {
            Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
            startActivity(intent);
        });

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
                float value = 0f;
                if (s.toString().isEmpty()) {
                    save.setBackgroundColor(getColor(R.color.greyed_magenta));
                    save.setEnabled(false);
                } else {
                    value = Float.parseFloat(s.toString()) * getRate();
                }
                calculatedValueText = getString(R.string.calculated_value, value);
                calculatedValueTextView.setText(calculatedValueText);
            }
        });

        ImageView xButton = findViewById(R.id.xbutton);
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAssetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
