package com.example.assets.activities;

import com.example.assets.asset_types.AssetType;

import java.util.HashMap;

public class ActivityMap {
    static HashMap<String, Class<?>> map;

    static {
        map = new HashMap<>();
        map.put(AssetType.CURRENCIES, AddCurrencyListActivity.class);
        map.put(AssetType.CRYPTOCCURRENCIES, AddCryptocurrencyListActivity.class);
        map.put(AssetType.STOCKS, AddStockListActivity.class);
        map.put(AssetType.NOBLE_METALS, AddNobleMetalListActivity.class);
        map.put(AssetType.ETFS, AddETFListActivity.class);
        map.put(AssetType.REAL_ESTATES, AddRealEstateActivity.class);
    }

    public static Class<?> getActivity(String assetType) {
        return map.get(assetType);
    }
}
