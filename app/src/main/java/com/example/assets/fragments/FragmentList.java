package com.example.assets.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentList {

    private List<FragmentValues> values = new ArrayList<>();
    private AssetDetailsTemplate template;

    int size() {
        return values.size();
    }

    AssetDetailsTemplate getTemplate() {
        return template;
    }

    public FragmentList(AssetDetailsTemplate template) {
        this.template = template;
    }

    public FragmentValues getFragmentValue(int position) {
        return values.get(position);
    }

    public FragmentList addFragments(FragmentValues... values) {
        for (FragmentValues value : values) {
            if (value.size() != template.size()) {
                throw new ArrayIndexOutOfBoundsException("Values size doesn't match template size.");
            }
        }
        this.values.addAll(Arrays.asList(values));
        return this;
    }
}
