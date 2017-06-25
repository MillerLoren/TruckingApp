package com.uprog.truckingapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etUserName,etUserPass;
    Button bRegister,bLogin;
    Spinner truckSpinner;
    String user_name,user_pass,truck_name,method;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fa = this;

        etUserName = (EditText)findViewById(R.id.etUserName);
        etUserPass = (EditText)findViewById(R.id.etUserPass);
        bRegister = (Button)findViewById(R.id.button_register);
        bRegister.setOnClickListener(this);
        bLogin = (Button)findViewById(R.id.button_login);
        bLogin.setOnClickListener(this);
        truckSpinner = (Spinner)findViewById(R.id.truckSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.truckOptions,R.layout.support_simple_spinner_dropdown_item);
        truckSpinner.setAdapter(adapter);
        truckSpinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button_register) {
            user_name = etUserName.getText().toString();
            user_pass = etUserPass.getText().toString();
            method = "Register";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, user_name, user_pass, truck_name);
        }
        else if(view.getId()==R.id.button_login){
            user_name = etUserName.getText().toString();
            user_pass = etUserPass.getText().toString();
            method = "Login";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method,user_name,user_pass);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        truck_name = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        truck_name = "Other";
    }
}
