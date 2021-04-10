package com.example.assets.storage.room.entity;

public class AssetDetails extends Asset {

    private Asset asset;
    private String baseCurrency;
    private float rate;
    private float value;

    public AssetDetails(Asset asset, BaseCurrency baseCurrency, float rate) {
        super(asset.getSymbol(), asset.getType(), asset.getQuantity(), asset.getInfo());
        this.asset = asset;
        this.baseCurrency = baseCurrency.getSymbol();
        this.rate = rate;
        this.value = rate * asset.getQuantity();
    }

    public Asset getAsset() {
        return asset;
    }

    public float getRate() {
        return rate;
    }

    public float getValue() {
        return value;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }
}
