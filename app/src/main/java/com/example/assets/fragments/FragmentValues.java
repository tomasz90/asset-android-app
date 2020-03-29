package com.example.assets.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentValues {

    private List<String> values;

    public FragmentValues(String... values) {
        this.values = Arrays.asList(values);
    }

    public String get(int position) {
        return values.get(position);
    }

    public int size() {
        return values.size();
    }
}
