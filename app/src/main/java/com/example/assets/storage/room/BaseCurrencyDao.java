package com.example.assets.storage.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BaseCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(BaseCurrency currency);

    @Query("SELECT * FROM base_currency_table WHERE ID = 1")
    LiveData<BaseCurrency> get();
}
