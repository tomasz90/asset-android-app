package com.example.assets.storage.room;

public class AssetDetails extends Asset {

    private Asset asset;
    private float rate;
    private float value;

    private BaseCurrency baseCurrency;

    public AssetDetails(Asset asset, float rate, BaseCurrency baseCurrency) {
        super(asset.getSymbol(), asset.getType(), asset.getQuantity(), asset.getInfo());
        this.asset = asset;
        this.rate = rate;
        this.value = rate * asset.getQuantity();
        this.baseCurrency = baseCurrency;
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

    public BaseCurrency getBaseCurrency() {
        return baseCurrency;
    }
}
