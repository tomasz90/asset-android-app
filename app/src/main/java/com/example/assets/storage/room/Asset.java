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
    private String info;

    public Asset(String symbol, String type, float quantity, String info) {
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.info = info;
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

    String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public float getQuantity() {
        return quantity;
    }
}
