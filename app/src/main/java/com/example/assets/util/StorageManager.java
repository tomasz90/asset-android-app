package com.example.assets.util;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.SneakyThrows;

public class StorageManager {

    private File file;

    public StorageManager(AppCompatActivity activity) {
        this.file = new File(activity.getFilesDir(), "db.json");
    }


    @SneakyThrows
    public JSONObject readFile() {
        String read = FileUtils.readFileToString(file, "UTF_8");
        return new JSONObject(read);
    }


    private boolean existFile() {
        return file.exists();
    }


    private void createFile() throws IOException {
        FileUtils.touch(file);
    }

    private void writeFile(JSONObject object) throws IOException {
        IOUtils.write(object.toString(), new FileOutputStream(file), "UTF-8");
    }

    @SneakyThrows
    public void deleteFile() {
        writeFile(new JSONObject());
    }

    public void addEntry(String symbol, String valueString) {
        try {
            if (existFile()) {
                float value = Float.parseFloat(valueString);
                JSONObject pastEntries = readFile();
                if (pastEntries.has(symbol)) {
                    value += Float.parseFloat(pastEntries.getString(symbol));
                }
                pastEntries.put(symbol, value);
                writeFile(pastEntries);
            } else {
                createFile();
                JSONObject newEntry = new JSONObject().put(symbol, valueString);
                writeFile(newEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
