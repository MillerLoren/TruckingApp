package com.uprog.truckingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Array of pages for table of contents.
    static final String[] PAGES = new String[] { "About", "Profile", "Settings", "Feedback", "Maps", "NavBar" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create ListView for table of contents.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,PAGES);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        //What to do when list item is clicked.
        TextView textView = (TextView)view;
        switch(textView.getText().toString()){
            case "NavBar":
                Intent nav = new Intent(this, NavDrawerActivity.class);
                startActivity(nav);
                break;
        }

        //Toast.makeText(MainActivity.this,"Go to " + textView.getText().toString() + " page.",Toast.LENGTH_LONG).show();
    }
}
