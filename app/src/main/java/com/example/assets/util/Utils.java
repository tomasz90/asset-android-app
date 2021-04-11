package com.example.assets.util;

import android.text.Editable;
import android.util.Pair;

import com.example.assets.storage.room.entity.BaseCurrency;

import org.json.JSONObject;

import java.util.Map;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CURRENCIES;

public class Utils {

    @SneakyThrows
    public static float getAssetRate(String type, String symbol, Pair<Map<String, Float>, BaseCurrency> pair) {
        if (pair.first != null && pair.second != null) {
            float rate = pair.first.get(symbol);
            float baseCurrencyRate = 1 /pair.first.get(pair.second.getSymbol());
            return rate * baseCurrencyRate;
        }
        return 0f;
    }

    public static void doNotAllowToEnterInvalidQuantity(Editable editable, char decimalSeparator) {
        String s = editable.toString();
        boolean isMoreThenOneSeparator = s.chars().filter(ch -> ch == decimalSeparator).count() > 1;
        boolean isSeparatorFirst = s.startsWith(String.valueOf(decimalSeparator));
        if (isMoreThenOneSeparator || isSeparatorFirst) {
            editable.delete(s.length() - 1, s.length());
        }
    }
}
