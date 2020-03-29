package com.example.assets.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fragment {

    private final List<Integer> viewIds;
    private final Integer fragmentId;
    private List<List<String>> values = new ArrayList<>();

    public Fragment(Integer fragmentId, Integer... viewIds) {
        this.fragmentId = fragmentId;
        this.viewIds = Arrays.asList(viewIds);
    }

    public void putRow(String... values) {
        List<String> singleDataRow = Arrays.asList(values);
        if(singleDataRow.size() != viewIds.size()) {
            throw new ArrayStoreException();
        }
        this.values.add(singleDataRow);
    }

    public List<Integer> getViewIds() {
        return viewIds;
    }

    public List<String> getRow(int position) {
        return values.get(position);
    }

    public int getSize() {
        return values.size();
    }

    public int getId() {
        return fragmentId;
    }
}
