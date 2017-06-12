package com.uprog.truckingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUserName,etUserPass;
    Button bRegister,bLogin;
    String user_name,user_pass,truck_name,method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etUserName = (EditText)findViewById(R.id.etUserName);
        etUserPass = (EditText)findViewById(R.id.etUserPass);
        bRegister = (Button)findViewById(R.id.button_register);
        bRegister.setOnClickListener(this);
        bLogin = (Button)findViewById(R.id.button_login);
        bLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button_register) {
            user_name = etUserName.getText().toString();
            user_pass = etUserPass.getText().toString();
            method = "Register";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, user_name, user_pass);
        }
        else if(view.getId()==R.id.button_login){
            user_name = etUserName.getText().toString();
            user_pass = etUserPass.getText().toString();
            method = "Login";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method,user_name,user_pass);
        }
    }
}
