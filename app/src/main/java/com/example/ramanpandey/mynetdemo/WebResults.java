package com.example.ramanpandey.mynetdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WebResults extends AppCompatActivity {

    TextView broad_cast_subject,broad_cast_msg,broad_cast_msg_username;
    String url1 = "http://10.0.2.2/mynet/broadcast_msg_subject_fetch.php";
    String url2 = "http://10.0.2.2/mynet/broadcast_msg_fetch.php";


    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recv_broadcast_msg);
        broad_cast_subject = (TextView)findViewById(R.id.recv_broadcast_subject);
        broad_cast_msg = (TextView) findViewById(R.id.recv_broadcast_msg);
        broad_cast_msg_username = (TextView)findViewById(R.id.recv_broadcast_msg_username);
        broad_cast_msg_username.setText(MainActivity.user_name);

        StringRequest postRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                broad_cast_subject.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WebResults.this,"Broadcast message Subject Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", MainActivity.user_name);

                return params;
            }
        };
        AppController.getInstance(WebResults.this).addToRequestQueue(postRequest1);

        StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                broad_cast_msg.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WebResults.this,"Broadcast message Body Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", MainActivity.user_name);

                return params;
            }
        };
        AppController.getInstance(WebResults.this).addToRequestQueue(postRequest2);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, BroadcastMessage.class));
    }
}
