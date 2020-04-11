package com.example.assets.storage.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "asset_table")
public class Asset {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String symbol;
    private String type;
    private float quantity;
    private String additionalInfo;

    public Asset(String symbol, String type, float quantity, String additionalInfo) {
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.additionalInfo = additionalInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public float getQuantity() {
        return quantity;
    }
}
