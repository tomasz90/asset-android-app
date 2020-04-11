package com.example.assets.asset_types;

import com.example.assets.activities.activities.AddCurrencyListActivity;

import java.util.HashMap;

public class ActivityMap {
    static HashMap<String, Class<?>> map;

    static {
        map = new HashMap<>();
        map.put(AssetType.CURRENCIES, AddCurrencyListActivity.class);
    }

    public static Class<?> getActivity(String assetType) {
        return map.get(assetType);
    }
}
