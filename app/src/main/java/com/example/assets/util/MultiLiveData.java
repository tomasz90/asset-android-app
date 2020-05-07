package com.example.assets.util;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import static com.example.assets.util.Utils.isAllNotNull;

public class MultiLiveData {

    // This custom live data contains trigger to force refresh UI
    public static class Quadruple<S, T, U, Boolean> extends MediatorLiveData<Quadruplet<S, T, U, Boolean>> {
        public Quadruple(@NonNull LiveData<S> live_s, @NonNull LiveData<T> live_t, @NonNull LiveData<U> live_u, @NonNull LiveData<Boolean> live_trigger) {
            addSource(live_s, s -> {
                T t = live_t.getValue();
                U u = live_u.getValue();
                Boolean trigger = live_trigger.getValue();
                setNotNullValueExceptTrigger(s, t, u, trigger);
            });

            addSource(live_t, t -> {
                S s = live_s.getValue();
                U u = live_u.getValue();
                Boolean trigger = live_trigger.getValue();
                setNotNullValueExceptTrigger(s, t, u, trigger);
            });

            addSource(live_u, u -> {
                S s = live_s.getValue();
                T t = live_t.getValue();
                Boolean trigger = live_trigger.getValue();
                setNotNullValueExceptTrigger(s, t, u, trigger);
            });

            addSource(live_trigger, trigger -> {
                S s = live_s.getValue();
                T t = live_t.getValue();
                U u = live_u.getValue();
                setNotNullValueExceptTrigger(s, t, u, trigger);
            });
        }

        private void setNotNullValueExceptTrigger(S s, T t, U u, Boolean trigger) {
            if (isAllNotNull(s, t, u)) {
                setValue(new Quadruplet<>(s, t, u, trigger));
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
                setValue(new Pair<>(s, t));
            }
        }
    }
}