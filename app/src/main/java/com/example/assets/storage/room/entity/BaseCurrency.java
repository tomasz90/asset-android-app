package com.example.assets.storage.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "base_currency_table")
public class BaseCurrency {

    @PrimaryKey
    private int id = 1;
    private String symbol;

    public BaseCurrency(String symbol) {
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setId(int id) {
        this.id = id;
    }
}
