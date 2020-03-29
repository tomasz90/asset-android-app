package com.example.assets.fragments;

import java.util.Arrays;
import java.util.List;

public class FragmentTemplate {

    public Integer getMainViewId() {
        return mainViewId;
    }

    private Integer mainViewId;
    private List<Integer> innerViewIds;

    public FragmentTemplate(Integer mainViewId, Integer... innerViewIds) {
        this.mainViewId = mainViewId;
        this.innerViewIds = Arrays.asList(innerViewIds);
    }

    List<Integer> getInnerViewIds() {
        return innerViewIds;
    }

    public int size() {
        return innerViewIds.size();
    }



}
