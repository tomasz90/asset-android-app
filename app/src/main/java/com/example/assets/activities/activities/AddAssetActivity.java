package com.example.assets.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assets.R;
import com.example.assets.constants.Constants;
import com.example.assets.storage.room.Asset;
import com.example.assets.storage.viewmodel.AssetViewModel;
import com.example.assets.util.ApiDataProvider;

public class AddAssetActivity extends AppCompatActivity {

    String assetSymbol;
    EditText editText;
    TextView calculatedValueTextView;
    Asset asset;
    float value;
    private AssetViewModel assetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        assetSymbol = getIntent().getStringExtra(Constants.ASSET);
        asset = (Asset) getIntent().getSerializableExtra("as");
        TextView symbolTextView = findViewById(R.id.asset_symbol);
        symbolTextView.setText(assetSymbol);

        calculatedValueTextView = findViewById(R.id.calculated_value);

        new ApiDataProvider(getApplication()).getData(false, dataFromApi -> {
            float rate = 1 / Float.parseFloat(dataFromApi.getString(assetSymbol));
            String textToDisplay = getString(R.string.rate, assetSymbol, rate);
            calculatedValueTextView.setText(textToDisplay);
        });

        Button saveButton = findViewById(R.id.fab);
        saveButton.setOnClickListener(view -> {
            assetViewModel = new ViewModelProvider(this).get(AssetViewModel.class);
            float setQuantity = Float.parseFloat(editText.getText().toString());
            if (asset == null) {
                assetViewModel.insertOrUpdate(new Asset(assetSymbol, "currency", setQuantity, "info"));
            } else {
                Asset newAsset = asset;
                // TODO: 4/14/2020 resolve update
                newAsset.setQuantity(setQuantity);
                assetViewModel.delete(asset);
                assetViewModel.insert(asset);
            }
            Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
            startActivity(intent);
        });

        ImageView closeButton = findViewById(R.id.xbutton);
        closeButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddAssetActivity.this, MainActivity.class);
            AddAssetActivity.this.startActivity(intent);
        });

        editText = findViewById(R.id.amount_input);
        if (asset != null) {
            editText.setText(getString(R.string.float_two_decimal, asset.getQuantity()));
        }
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
                    value = Float.parseFloat(s.toString().replace(",","."));
                }
                new ApiDataProvider(getApplication()).getData(false, data -> {
                    float rate = 1 / Float.parseFloat(data.getString(assetSymbol));
                    String textToDisplay = getString(R.string.calculated_value, value * rate);
                    calculatedValueTextView.setText(textToDisplay);
                });
            }
        });
    }
}
