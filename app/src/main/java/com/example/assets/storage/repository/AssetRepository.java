package com.example.assets.storage.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.assets.storage.room.Asset;
import com.example.assets.storage.room.AssetDao;
import com.example.assets.storage.room.NoteDataBase;

import java.util.List;

public class AssetRepository {
    private AssetDao assetDao;
    private LiveData<List<Asset>> allAssets;

    public AssetRepository(Application application) {
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        assetDao = dataBase.assetDao();
        allAssets = assetDao.getAll();
    }


    public void insert(Asset asset) {
        new InsertNoteAsyncTask(assetDao).execute(asset);
    }

    public void update(Asset asset) {
        new UpdateNoteAsyncTask(assetDao).execute(asset);
    }

    public void delete(Asset asset) {
        new DeleteNoteAsyncTask(assetDao).execute(asset);
    }

    public void deleteAll() {
        new DeleteAllNoteAsyncTask(assetDao).execute();
    }

    public LiveData<List<Asset>> getAll() {
        return allAssets;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Asset, Void, Void> {

        private AssetDao assetDao;

        InsertNoteAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... assets) {
            assetDao.insert(assets[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Asset, Void, Void> {

        private AssetDao assetDao;

        UpdateNoteAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... assets) {
            assetDao.update(assets[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Asset, Void, Void> {

        private AssetDao assetDao;

        DeleteNoteAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... assets) {
            assetDao.delete(assets[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private AssetDao assetDao;

        DeleteAllNoteAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            assetDao.deleteAll();
            return null;
        }
    }
}
