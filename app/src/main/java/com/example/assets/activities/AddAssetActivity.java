package com.example.assets.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

public class AddAssetActivity extends AppCompatActivity {

    TextView valueLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        String assetSymbol = getIntent().getStringExtra("asset");
        setAssetSymbol(assetSymbol);

        valueLabel = findViewById(R.id.calculated_value);

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
                    value = Float.parseFloat(s.toString()) * getRate(assetSymbol);
                }
                textLabel = getString(R.string.calculated_value, value);
                valueLabel.setText(textLabel);
            }
        });
        new GetJSONTask().execute();

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

    private float getRate(String assetSymbol) {
        return 1f;
    }

    private class GetJSONTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return CurrencyService.getRate();
            } catch (Exception e) {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            float rate = Float.parseFloat(result);
            valueLabel.setText(String.format("%.2f", rate));
        }
    }
}
