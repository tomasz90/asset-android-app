package com.example.assets.storage.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.assets.storage.room.dao.AssetDao;
import com.example.assets.storage.room.dao.BaseCurrencyDao;
import com.example.assets.storage.room.entity.Asset;
import com.example.assets.storage.room.entity.BaseCurrency;

@Database(entities = {Asset.class, BaseCurrency.class}, version = 1)
public abstract class AssetDataBase extends RoomDatabase {

    private static AssetDataBase instance;

    public abstract AssetDao assetDao();
    public abstract BaseCurrencyDao baseCurrencyDao();

    public static synchronized AssetDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), AssetDataBase.class, "asset_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO base_currency_table (symbol) VALUES ('USD')");
        }
    };
}
