package com.example.assets.storage.room;

public class AssetDetails extends Asset {

    private Asset asset;
    private String rate;
    private String value;

    public AssetDetails(Asset asset, String rate) {
        super(asset.getSymbol(), asset.getType(), asset.getQuantity(), asset.getAdditionalInfo());
        this.asset = asset;
        this.rate = rate;
        try {
            this.value = String.valueOf(1 / Float.parseFloat(rate) * asset.getQuantity());
        } catch (Exception e) {
            this.value = "-";
        }
    }

    public Asset getAsset() {
        return asset;
    }

    public String getRate() {
        return rate;
    }

    public String getValue() {
        return value;
    }
}
