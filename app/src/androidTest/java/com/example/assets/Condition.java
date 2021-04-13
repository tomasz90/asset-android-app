package com.example.assets;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.azimolabs.conditionwatcher.Instruction;
import com.example.assets.activity.MainActivity;

import java.util.Collection;
import java.util.Objects;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;

class Condition {

    public static class LoadingMainActivity extends Instruction {
        @Override
        public String getDescription() {
            return "wait until MainActivity loaded";
        }

        @Override
        public boolean checkCondition() {
            Activity activity = getActivityInstance();
            return activity instanceof MainActivity;
        }
    }

    public static class LoadingListItems extends Instruction {

        LoadingListItems(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        private RecyclerView recyclerView;

        @Override
        public String getDescription() {
            return "wait until list loaded";
        }

        @Override
        public boolean checkCondition() {
            return Objects.requireNonNull(recyclerView.getAdapter()).getItemCount() != 0;
        }
    }

    private static Activity currentActivity = null;

    private static Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities =
                    ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()){
                currentActivity = resumedActivities.iterator().next();
            }
        });
        return currentActivity;
    }
}
