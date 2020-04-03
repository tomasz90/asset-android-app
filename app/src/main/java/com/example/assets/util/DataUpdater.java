package com.example.assets.util;

import org.json.JSONObject;

public interface DataUpdater {

    void updateUI(JSONObject object, String action);
}
