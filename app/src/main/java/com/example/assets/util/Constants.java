package com.example.assets.util;

import com.example.assets.activity.AssetTypeListActivity;
import com.example.assets.api.client.Cryptos;
import com.example.assets.api.client.Currencies;
import com.example.assets.api.client.Metals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Constants {

    public static final String ASSET_TYPE = "assetType";
    public static final String SPECIFIC_ASSETS = "specificAssets";
    public static final String EDITED_ASSET = "editedAsset";
    public static final Map<String, ArrayList<String>> assetMap = new HashMap<>();

    static {
        assetMap.put(AssetTypeListActivity.AssetTypes.CRYPTOS.name(), asStringList(Cryptos.values()));
        assetMap.put(AssetTypeListActivity.AssetTypes.CURRENCIES.name(), asStringList(Currencies.values()));
        assetMap.put(AssetTypeListActivity.AssetTypes.METALS.name(), asStringList(Metals.values()));
    }

    static ArrayList<String> asStringList(Object[] values) {
        return (ArrayList<String>) Arrays.stream(values).map(Object::toString).collect(Collectors.toList());
    }
}
