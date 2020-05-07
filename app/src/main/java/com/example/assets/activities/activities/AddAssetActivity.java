package com.example.assets.activities.activities;

import android.content.Intent;
import android.icu.text.DecimalFormatSymbols;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assets.R;
import com.example.assets.constants.AssetConstants;
import com.example.assets.constants.Constants;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.viewmodel.AddAssetViewModel;
import com.example.assets.util.Utils;

import java.util.Objects;

import lombok.SneakyThrows;

import static com.example.assets.util.Utils.doNotAllowToEnterInvalidQuantity;
import static com.example.assets.util.Utils.getAssetRate;

public class AddAssetActivity extends AppCompatActivity {

    private float value;
    private char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        AddAssetViewModel addAssetViewModel = new ViewModelProvider(this).get(AddAssetViewModel.class);

        // Find all views
        TextView calculatedValueTextView = findViewById(R.id.calculated_value);
        TextView symbolTextView = findViewById(R.id.asset_symbol);
        EditText editText = findViewById(R.id.amount_input);
        Button saveButton = findViewById(R.id.fab);
        ImageView closeButton = findViewById(R.id.xbutton);

        // Get data intent from previous activity
        Asset asset = (Asset) getIntent().getSerializableExtra(AssetConstants.EDITED_ASSET);
        String assetType = Objects.requireNonNull(asset).getType();
        String assetSymbol = asset.getSymbol();

        // Set main asset symbol
        symbolTextView.setText(assetSymbol);

        // Initially set rate of asset
        addAssetViewModel.getRatesAndBaseCurrency().observe(AddAssetActivity.this, pair -> {
                float rate = getAssetRate(assetType, assetSymbol, pair);
                calculatedValueTextView.setText(getString(R.string.rate, assetSymbol, pair.second.getSymbol(), rate));
        });

        // Prepare editText -> set decimal char AND initial value
        prepareEditText(editText, asset);

        // Actions on entering values into editText
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
                    doNotAllowToEnterInvalidQuantity(s, decimalSeparator);
                    value = Utils.toFloat(s);
                }

                addAssetViewModel.getRatesAndBaseCurrency().observe(AddAssetActivity.this, pair -> {
                    float rate = getAssetRate(assetType, assetSymbol, pair);
                    String textToDisplay = getString(R.string.calculated_value, value * rate, pair.second.getSymbol());
                    calculatedValueTextView.setText(textToDisplay);
                });
            }
        });

        // Save asset -> insert new asset OR increment asset value when exists OR update asset value when edit
        saveButton.setOnClickListener(view -> {
            float quantity = Utils.toFloat(editText.getText());
            if (isAssetEdited(asset)) {
                asset.setQuantity(quantity);
                addAssetViewModel.updateAsset(asset);
            } else {
                addAssetViewModel.upsertAsset(new Asset(assetSymbol, assetType, quantity, ""));
            }
            Intent intent = new Intent(AddAssetActivity.this, DoneActivity.class);
            startActivity(intent);
        });

        // Close activity without any action
        closeButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddAssetActivity.this, MainActivity.class);
            AddAssetActivity.this.startActivity(intent);
        });
    }

    private void prepareEditText(EditText editText, Asset asset) {
        editText.setKeyListener(DigitsKeyListener.getInstance(Constants.DIGITS + decimalSeparator));
        if (isAssetEdited(asset)) {
            editText.setText(getString(R.string.float_two_decimal, asset.getQuantity()));
        }
    }

    private boolean isAssetEdited(Asset asset) {
        return Objects.requireNonNull(asset).getQuantity() > 0;
    }
}
