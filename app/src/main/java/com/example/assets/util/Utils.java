package com.example.assets.util;

public class Utils {

    public static float toFloat(Object o) {
        String s = o.toString();
        if (!s.isEmpty()) {
            return Float.parseFloat(o.toString().replace(",", "."));
        }
        return 0f;
    }
}
