package com.example.assets.activities.activities;

import android.content.Intent;
import android.icu.text.DecimalFormatSymbols;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assets.R;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;
import com.example.assets.storage.viewmodel.AddAssetViewModel;
import com.example.assets.util.Constants;

import java.util.Map;
import java.util.Objects;

import lombok.SneakyThrows;

public class AddAssetActivity extends AppCompatActivity {

    private float value;
    private char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_asset_activity_title);

        AddAssetViewModel addAssetViewModel = new ViewModelProvider(this).get(AddAssetViewModel.class);

        // Find all views
        TextView calculatedValueTextView = findViewById(R.id.calculated_value);
        TextView symbolTextView = findViewById(R.id.asset_symbol);
        EditText editText = findViewById(R.id.amount_input);
        Button saveButton = findViewById(R.id.fab);
        ImageView closeButton = findViewById(R.id.xbutton);

        // Get data intent from previous activity
        Asset asset = (Asset) getIntent().getSerializableExtra(Constants.EDITED_ASSET);
        String assetType = Objects.requireNonNull(asset).getType();
        String assetSymbol = asset.getSymbol();

        // Set main asset symbol
        symbolTextView.setText(assetSymbol);

        // Initially set rate of asset
        addAssetViewModel.getRatesAndBaseCurrency().observe(AddAssetActivity.this, pair -> {
                float rate = getAssetRate(assetSymbol, pair);
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
                    value = toFloat(s);
                }

                addAssetViewModel.getRatesAndBaseCurrency().observe(AddAssetActivity.this, pair -> {
                    float rate = getAssetRate(assetSymbol, pair);
                    String textToDisplay = getString(R.string.calculated_value, value * rate, pair.second.getSymbol());
                    calculatedValueTextView.setText(textToDisplay);
                });
            }
        });

        // Save asset -> insert new asset OR increment asset value when exists OR update asset value when edit
        saveButton.setOnClickListener(view -> {
            float quantity = toFloat(editText.getText());
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

    private float toFloat(Object o) {
        String s = o.toString();
        if (!s.isEmpty()) {
            return Float.parseFloat(o.toString().replace(",", "."));
        }
        return 0f;
    }

    @SneakyThrows
    private float getAssetRate(String symbol, Pair<Map<String, Float>, BaseCurrency> pair) {
        if (pair.first != null && pair.second != null) {
            float rate = pair.first.get(symbol);
            float baseCurrencyRate = 1 / pair.first.get(pair.second.getSymbol());
            return rate * baseCurrencyRate;
        }
        return 0f;
    }

    private void doNotAllowToEnterInvalidQuantity(Editable editable, char decimalSeparator) {
        String s = editable.toString();
        boolean isMoreThenOneSeparator = s.chars().filter(ch -> ch == decimalSeparator).count() > 1;
        boolean isSeparatorFirst = s.startsWith(String.valueOf(decimalSeparator));
        if (isMoreThenOneSeparator || isSeparatorFirst) {
            editable.delete(s.length() - 1, s.length());
        }
    }
}
