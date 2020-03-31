package com.example.assets.util;

import org.json.JSONObject;

import java.util.Iterator;

import lombok.SneakyThrows;

public class ValueCalculator {

    @SneakyThrows
    public static String calculateTotal(JSONObject assets, JSONObject rates) {
        float sum = 0f;
        Iterator<String> keys = assets.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            float rate = 1 / Float.parseFloat(rates.getString(key));
            float quantity = Float.parseFloat(assets.getString(key));
            sum += quantity*rate;
        }
        return String.valueOf((int) sum);
    }
}
