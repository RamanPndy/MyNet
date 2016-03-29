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

public class FullMessageActivity extends AppCompatActivity {
    String url1 = "http://10.0.2.2/mynet/show_full_msg.php";
    String url2 = "http://10.0.2.2/mynet/delete_msg.php";
    TextView msg_subject,msg_body;
    SharedPreferences sharedpreferences;
    String Msg_Subject,User_Name;
    Button DelMsgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_message);
        msg_subject = (TextView)findViewById(R.id.fullmsgSubject);
        msg_body = (TextView)findViewById(R.id.fullmsgBody);
        DelMsgBtn = (Button)findViewById(R.id.deleteMsgBtn);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Msg_Subject = sharedpreferences.getString(MainActivity.BroadCastSubject, "");
        User_Name = sharedpreferences.getString(MainActivity.UserName,"");
        msg_subject.setText(Msg_Subject);
//        DelMsgBtn.setVisibility(View.INVISIBLE);

        DelMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  s) {

                        Toast.makeText(FullMessageActivity.this, "Message Deleted Successfully !!!", Toast.LENGTH_SHORT).show();
                        Intent goToAdminProfileActivity = new Intent(FullMessageActivity.this,ShowPrevMessagesActivity.class);
                        startActivity(goToAdminProfileActivity);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(FullMessageActivity.this,"Message Deletion Failed",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("msg_subject", Msg_Subject);
                        params.put("admin_name", User_Name);

                        return params;
                    }
                };
                AppController.getInstance(FullMessageActivity.this).addToRequestQueue(postRequest2);
            }
        });

        StringRequest postRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(FullMessageActivity.this,s,Toast.LENGTH_SHORT).show();
                msg_body.setText(s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FullMessageActivity.this,"Full  message Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("broadcast_subject", Msg_Subject);
                params.put("user_name", User_Name);

                return params;
            }
        };
        AppController.getInstance(FullMessageActivity.this).addToRequestQueue(postRequest1);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ShowPrevMessagesActivity.class));
    }
}
