package com.example.assets.util;

import android.annotation.SuppressLint;

public class Formatter {

    @SuppressLint("DefaultLocale")
    public static String formatFloat(String number) {
        return  String.format("%.2f", number);
    }
}
