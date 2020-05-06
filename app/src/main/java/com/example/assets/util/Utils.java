package com.example.assets.util;

import android.util.Pair;

import com.example.assets.storage.room.BaseCurrency;

import org.json.JSONObject;

import lombok.SneakyThrows;

import static com.example.assets.constants.AssetConstants.CURRENCIES;

public class Utils {

    public static float toFloat(Object o) {
        String s = o.toString();
        if (!s.isEmpty()) {
            return Float.parseFloat(o.toString().replace(",", "."));
        }
        return 0f;
    }

    @SneakyThrows
    public static float getAssetRate(String type, String symbol, Pair<JSONObject, BaseCurrency> pair) {
        if (pair.first != null && pair.second != null) {
            float rate = Utils.toFloat(pair.first.getJSONObject(type).getString(symbol));
            float baseCurrencyRate = 1 / Utils.toFloat(pair.first.getJSONObject(CURRENCIES).getString(pair.second.getSymbol()));
            return rate * baseCurrencyRate;
        }
        return 0f;
    }
}
