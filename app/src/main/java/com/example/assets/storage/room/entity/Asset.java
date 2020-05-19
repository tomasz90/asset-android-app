package com.example.assets.storage.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "asset_table")
public class Asset implements Serializable {

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

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
