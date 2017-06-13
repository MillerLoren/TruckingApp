package com.uprog.truckingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by rm_ch on 6/10/2017.
 */

public class BackgroundTask extends AsyncTask<String, Object, String> {

    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx){
        this.ctx = ctx;
    }
    String user_name,user_pass;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(ctx).create();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://uprog.gear.host/register.php"; //temporary development host
        String ret_url = "http://uprog.gear.host/retrieve.php"; //temporary development host
        String json_url = "http://uprog.gear.host/get_data.php"; //temporary development host
        String method = params[0];
        if(method.equals("Register")){
            user_name = params[1];
            user_pass = params[2];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name","UTF-8") + "=" + URLEncoder.encode(user_name,"UTF-8") +"&"+
                        URLEncoder.encode("user_pass","UTF-8") + "=" + URLEncoder.encode(user_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                String response = "";
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                }
                else{
                    response = "Error connecting to server.";
                }
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(method.equals("Login")){
            String login_name = params[1];
            String login_pass = params[2];
            try {
                URL url = new URL(ret_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8") + "=" + URLEncoder.encode(login_name,"UTF-8") + "&" +
                                URLEncoder.encode("login_pass","UTF-8") + "=" + URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                String response = "";
                if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                }
                else{
                    response = "Error connecting to server.";
                }
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("getJSON")){

            String JSON_STRING;
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                StringBuilder stringBuilder = new StringBuilder();
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((JSON_STRING = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                }
                else{
                    stringBuilder.append("Error connecting to server.");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.contains("Duplicate entry")) {
            alertDialog.setTitle("Registration");
            alertDialog.setMessage("Please choose another username. "+user_name + " is already taken.");
        }
        else if(result.contains("inserted")){
            alertDialog.setTitle("Registration");
            alertDialog.setMessage(result);
        }
        else {
            alertDialog.setTitle("Login Information");
            alertDialog.setMessage(result);
        }

        alertDialog.show();
    }
}
