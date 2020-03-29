package com.example.assets.activities;

import com.example.assets.asset_types.AssetType;

import java.util.HashMap;

public class ActivityMap {
    static HashMap<String, Class<?>> map;

    static {
        map = new HashMap<>();
        map.put(AssetType.CURRENCIES, AddCurrencyActivity.class);
        map.put(AssetType.CRYPTOCCURRENCIES, AddCryptocurrencyActivity.class);
        map.put(AssetType.STOCKS, AddStockActivity.class);
        map.put(AssetType.NOBLE_METALS, AddNobleMetalActivity.class);
        map.put(AssetType.ETFS, AddETFActivity.class);
        map.put(AssetType.REAL_ESTATES, AddRealEstateActivity.class);
    }

    public static Class<?> getActivity(String assetType) {
        return map.get(assetType);
    }
}
