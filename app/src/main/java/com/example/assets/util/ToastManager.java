package com.example.assets.util;

import android.app.Application;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

public class ToastManager {

    public static void displayToast(Application application) {
        Toast toast = Toast.makeText(application, "Network not available :(", Toast.LENGTH_SHORT);

        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(application.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(application.getColor(R.color.white));
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
