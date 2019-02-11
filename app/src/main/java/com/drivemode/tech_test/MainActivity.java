package com.drivemode.tech_test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonView{

    public static final String TAG = MainActivity.class.getSimpleName();
    private View progress;
    private Button doWorkBtn;
    ArrayList<Person> personList;

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("persons", personList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setData(savedInstanceState.<Person>getParcelableArrayList("persons"));
    }

    public void setData(List<Person> personList) {
        this.personList = (ArrayList<Person>) personList;
        ListAdapter adapter = new MyAdapter(this, personList);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public void warnEmptyLIst() {
        Toast.makeText(this, "Person list is not present.", Toast.LENGTH_LONG).show();
    }

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        doWorkBtn.setActivated(false);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
        doWorkBtn.setActivated(true);
    }


    private static class MyAdapter extends ArrayAdapter<Person> {

        MyAdapter(Context context, List<Person> list) {
            super(context, android.R.layout.simple_list_item_2, android.R.id.text1, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView nameTextView = view.findViewById(android.R.id.text1);
            TextView descriptionTextView = view.findViewById(android.R.id.text2);
            nameTextView.setText(getItem(position).getMainInfo());
            descriptionTextView.setText(getItem(position).getAdditionalInfo());

            return view;
        }
    }
}
