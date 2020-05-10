package com.example.assets.util;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assets.R;

public class Dialog {

    public static ProgressDialog displayLoading(Activity activity) {
        ProgressDialog mDialog = new ProgressDialog(activity);
        mDialog.setMessage(activity.getString(R.string.loading));
        mDialog.setCancelable(false);
        mDialog.show();
        return mDialog;
    }

    public static void displayToast(Application application, int resourceString) {
        String text = application.getString(resourceString);
        Toast toast = Toast.makeText(application, text, Toast.LENGTH_SHORT);

        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(application.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be edited
        TextView textView = view.findViewById(android.R.id.message);
        textView.setTextColor(application.getColor(R.color.white));
        toast.setGravity(Gravity.CENTER, 0, 300);
        toast.show();
    }
}
