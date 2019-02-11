package com.drivemode.tech_test;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.drivemode.tech_test.MainActivity.TAG;

public class JsonFetcherAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    private URL url;
    private PersonView activityRef;

    JsonFetcherAsyncTask(String url, MainActivity activity) {

        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.activityRef = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activityRef.showProgress();
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
    protected void onPostExecute(@Nullable JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        activityRef.hideProgress();
        try {
            if (jsonObject != null) {
                activityRef.setData(convert(jsonObject.getJSONArray("data")));
            } else {
                activityRef.warnEmptyLIst();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<Person> convert(JSONArray array) {
        List<Person> list = new ArrayList<>(array.length());
        JSONObject jsonObject;
        for (int i = 0, count = array.length(); i < count; i++) {
            try {
                if ((jsonObject = array.getJSONObject(i)) != null) {
                    list.add(new Person(jsonObject));
                } else {
                    list.add(new Person());
                }
            } catch (JSONException e) {
                Log.e(TAG, "invalid json", e);
            }
        }
        return list;
    }
}
