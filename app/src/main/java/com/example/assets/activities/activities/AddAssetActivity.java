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
import com.example.assets.storage.room.Asset;
import com.example.assets.storage.viewmodel.AssetViewModel;
import com.example.assets.util.ApiDataProvider;
import com.example.assets.util.Utils;

public class AddAssetActivity extends AppCompatActivity {

    private String assetSymbol;
    private String assetType;
    private EditText editText;
    private TextView calculatedValueTextView;
    private Asset editedAsset;
    private AssetViewModel assetViewModel;
    private float value;
    private char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        editedAsset = (Asset) getIntent().getSerializableExtra(AssetConstants.EDITED_ASSET);
        assetType = getIntent().getStringExtra(AssetConstants.ASSET_TYPE);

        if (assetType == null) {
            assetType = editedAsset.getType();
        }

        assetSymbol = getIntent().getStringExtra(AssetConstants.ASSET_SYMBOL);

        TextView symbolTextView = findViewById(R.id.asset_symbol);
        symbolTextView.setText(assetSymbol);

        calculatedValueTextView = findViewById(R.id.calculated_value);

        new ApiDataProvider(getApplication()).getData(false, dataFromApi -> {
            float rate = Utils.toFloat(dataFromApi.getJSONObject(assetType).getString(assetSymbol));
            String textToDisplay = getString(R.string.rate, assetSymbol, rate);
            calculatedValueTextView.setText(textToDisplay);
        });

        Button saveButton = findViewById(R.id.fab);
        saveButton.setOnClickListener(view -> {
            assetViewModel = new ViewModelProvider(this).get(AssetViewModel.class);
            float setQuantity = Utils.toFloat(editText.getText());
            if (editedAsset == null) {
                assetViewModel.insertOrUpdate(new Asset(assetSymbol, assetType, setQuantity, ""));
            } else {
                Asset newAsset = editedAsset;
                newAsset.setQuantity(setQuantity);
                assetViewModel.update(editedAsset);
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
        editText.setKeyListener(DigitsKeyListener.getInstance(Constants.DIGITS + decimalSeparator));

        if (editedAsset != null) {
            editText.setText(getString(R.string.float_two_decimal, editedAsset.getQuantity()));
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
                    doNotAllowToEnterInvalidQuantity(s);
                    value = Utils.toFloat(s);
                }
                new ApiDataProvider(getApplication()).getData(false, dataFromApi -> {
                    float rate = Utils.toFloat(dataFromApi.getJSONObject(assetType).getString(assetSymbol));
                    String textToDisplay = getString(R.string.calculated_value, value * rate);
                    calculatedValueTextView.setText(textToDisplay);
                });
            }
        });
    }

   private void doNotAllowToEnterInvalidQuantity(Editable editable) {
        String s = editable.toString();
        boolean isMoreThenOneSeparator = s.chars().filter(ch -> ch == decimalSeparator).count() > 1;
        boolean isSeparatorFirst = s.startsWith(String.valueOf(decimalSeparator));
        if (isMoreThenOneSeparator || isSeparatorFirst ) {
            editable.delete(s.length() - 1, s.length());
        }
    }
}
