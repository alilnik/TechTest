package com.drivemode.tech_test;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private View progress;
    private Button doWorkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progress = findViewById(R.id.progress);
        doWorkBtn = findViewById(R.id.button_do_the_work);

        doWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonFetcherAsyncTask("http://www.mocky.io/v2/588561b60f0000dd1fff654c", MainActivity.this).execute();
            }
        });
    }

    void setData(JSONArray array) {
        ListAdapter adapter = new MyAdapter(this, convert(array));
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    List<JSONObject> convert(JSONArray array) {
        List<JSONObject> list = new ArrayList<>(array.length());
        for (int i = 0, count = array.length(); i < count; i++) {
            try {
                list.add(i, array.getJSONObject(i));
            } catch (JSONException e) {
                Log.e(TAG, "invalid json", e);
            }
        }
        return list;
    }

    void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public Button getDoWorkBtn() {
        return doWorkBtn;
    }


    private static class MyAdapter extends ArrayAdapter<JSONObject> {

        private Person person;

        MyAdapter(Context context, List<JSONObject> list) {
            super(context, android.R.layout.simple_list_item_2, android.R.id.text1, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView nameTextView = view.findViewById(android.R.id.text1);
            TextView descriptionTextView = view.findViewById(android.R.id.text2);

            JSONObject jsonItem = getItem(position);
            if (jsonItem != null){
                person = new Person(jsonItem);
            } else {
                person = new Person();
            }

            nameTextView.setText(person.getMainInfo());
            descriptionTextView.setText(person.getAdditionalInfo());

            return view;
        }
    }
}
