package com.example.assets.storage.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "base_currency_table")
public class BaseCurrency {

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey
    private int id = 1;
    private String symbol;
    private float rate;

    public BaseCurrency(String symbol, float rate) {
        this.symbol = symbol;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
