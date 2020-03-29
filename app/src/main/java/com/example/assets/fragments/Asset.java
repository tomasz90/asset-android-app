package com.example.assets.fragments;


public class Asset {

    public String getAssetName() {
        return assetName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getUnits() {
        return units;
    }

    public int getValue() {
        return value;
    }

    private String assetName;
    private float unitPrice;
    private float units;
    private int value;

    public Asset(String assetName, float unitPrice, float units) {
        this.assetName = assetName;
        this.unitPrice = unitPrice;
        this.units = units;
        this.value = (int) (unitPrice * units);
    }
}
