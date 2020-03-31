package com.example.assets.activities;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assets.R;

public class ActivityExample extends AppCompatActivity {

        private TextView tvData;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_example);
            tvData = (TextView) findViewById(R.id.holder);
            tvData.setMovementMethod(new ScrollingMovementMethod());

            new GetJSONTask().execute();
        }

        private class GetJSONTask extends AsyncTask<String, Void, String> {
            private ProgressDialog pd;

            // onPreExecute called before the doInBackgroud start for display
            // progress dialog.
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ProgressDialog.show(ActivityExample.this, "", "Loading", true,
                        false); // Create and show Progress dialog
            }

            @Override
            protected String doInBackground(String... urls) {
                try {
                    return CurrencyService.getRate();
                } catch (Exception e) {
                    return "Unable to retrieve data. URL may be invalid.";
                }
            }

            // onPostExecute displays the results of the doInBackgroud and also we
            // can hide progress dialog.
            @Override
            protected void onPostExecute(String result) {
                pd.dismiss();
                tvData.setText(result);
            }
        }
    }
