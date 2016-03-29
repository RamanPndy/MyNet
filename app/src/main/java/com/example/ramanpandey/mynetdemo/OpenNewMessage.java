package com.example.ramanpandey.mynetdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OpenNewMessage extends AppCompatActivity {
    String url1 = "http://10.0.2.2/mynet/open_new_msg.php";
    String url2 = "http://10.0.2.2/mynet/update_read_flag.php";
    TextView msg_subject,msg_body;
    SharedPreferences sharedpreferences;
    String Msg_Subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_new_msg);
        msg_subject = (TextView)findViewById(R.id.newMsgSubject);
        msg_body = (TextView)findViewById(R.id.newMsgBody);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Msg_Subject = sharedpreferences.getString(MainActivity.BroadCastSubject, "");
        msg_subject.setText(Msg_Subject);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(OpenNewMessage.this,s,Toast.LENGTH_SHORT).show();
                msg_body.setText(s);

                StringRequest postRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  s) {
                        //Toast.makeText(OpenNewMessage.this,s,Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(OpenNewMessage.this,"Message Updating Failed",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("broadcast_subject", Msg_Subject);


                        return params;
                    }
                };
                AppController.getInstance(OpenNewMessage.this).addToRequestQueue(postRequest);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(OpenNewMessage.this,"Full  message Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("broadcast_subject", Msg_Subject);
                

                return params;
            }
        };
        AppController.getInstance(OpenNewMessage.this).addToRequestQueue(postRequest);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ShowNewMessageActivity.class));
    }
}
