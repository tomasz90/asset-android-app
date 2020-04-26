package com.example.assets.util;

public class Utils {

    public static float toFloat(Object o) {
        return Float.parseFloat(o.toString().replace(",","."));
    }
}
