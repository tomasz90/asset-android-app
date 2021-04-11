package com.example.assets.util.custom_livedata;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class MultiLiveData {

    // This custom live data contains trigger to force refresh UI. It may not be intuitive for liveData, but there are some cases:
    // ex. user swipe asset to the right to delete it, then decline. Item is no longer visible, but is not deleted.
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

    private static boolean isAllNotNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }
}