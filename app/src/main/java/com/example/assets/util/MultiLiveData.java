package com.example.assets.util;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import static com.example.assets.util.Utils.isAllNotNull;

public class MultiLiveData {

    public static class Triple<S, T, U> extends MediatorLiveData<Triplet<S, T, U>> {
        public Triple(@NonNull LiveData<S> live_s, @NonNull LiveData<T> live_t, @NonNull LiveData<U> live_u) {
            addSource(live_s, s -> {
                T t = live_t.getValue();
                U u = live_u.getValue();
                setNotNullValue(s, t, u);
            });

            addSource(live_t, t -> {
                S s = live_s.getValue();
                U u = live_u.getValue();
                setNotNullValue(s, t, u);
            });

            addSource(live_u, u -> {
                S s = live_s.getValue();
                T t = live_t.getValue();
                setNotNullValue(s, t, u);
            });
        }

        private void setNotNullValue(S s, T t, U u) {
            if (isAllNotNull(s, t, u)) {
                setValue(new Triplet(s, t, u));
            }
        }
    }

    public static class Double<S, T> extends MediatorLiveData<Pair<S, T>> {
        public Double(@NonNull LiveData<S> live_s, @NonNull LiveData<T> live_t) {
            addSource(live_s, s -> {
                T t = live_t.getValue();
                setNotNullValue(s, t);
            });

            addSource(live_t, t -> {
                S s = live_s.getValue();
                setNotNullValue(s, t);
            });
        }

        private void setNotNullValue(S s, T t) {
            if (isAllNotNull(s, t)) {
                setValue(new Pair(s, t));
            }
        }
    }
}