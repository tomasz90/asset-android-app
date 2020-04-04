package com.example.assets.util;

import org.json.JSONException;
import org.json.JSONObject;

public interface DataUpdater {

    void updateUI(JSONObject dataFromApi) throws JSONException;
}
