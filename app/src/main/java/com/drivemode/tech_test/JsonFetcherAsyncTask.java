package com.drivemode.tech_test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.drivemode.tech_test.MainActivity.TAG;

public class JsonFetcherAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    private URL url;
    private WeakReference<MainActivity> activityRef;

    JsonFetcherAsyncTask(String url, MainActivity activity) {

        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.activityRef = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activityRef.get().showProgress();
        activityRef.get().getDoWorkBtn().setActivated(false);
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
            return new JSONObject(builder.toString());

        } catch (MalformedURLException e) {
            Log.e(TAG, "malformed url", e);
        } catch (IOException e) {
            Log.e(TAG, "io error", e);
        } catch (JSONException e) {
            Log.e(TAG, "invalid json", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "", e);
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        activityRef.get().hideProgress();
        try {
            activityRef.get().setData(jsonObject.getJSONArray("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        activityRef.get().getDoWorkBtn().setActivated(true);
    }
}
