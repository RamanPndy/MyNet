package com.example.ramanpandey.mynetdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAdminProfile extends AppCompatActivity {
    TextView admin_name,admin_email;
    String url1 = "http://10.0.2.2/mynet/get_admin_profile.php";
    String url2 = "http://10.0.2.2/mynet/send_join_request.php";
    String url3 = "http://10.0.2.2/mynet/check_join_request.php";
    String url4 = "http://10.0.2.2/mynet/check_admin_status.php";
    Button sendJoinRequest;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_admin_profile);
        admin_name = (TextView)findViewById(R.id.openAdminProfileAdminName);
        admin_email = (TextView)findViewById(R.id.openAdminProfileAdminEmail);
        sendJoinRequest = (Button)findViewById(R.id.sendJoinRequestButton);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest4 = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(OpenAdminProfile.this,s,Toast.LENGTH_SHORT).show();
                if(s.replace(",","").equals(sharedpreferences.getString(MainActivity.UserName,""))) {
                    sendJoinRequest.setVisibility(View.INVISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(OpenAdminProfile.this,"Admin Status Check Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("admin_name",sharedpreferences.getString(MainActivity.AdminName,""));

                return params;
            }
        };
        AppController.getInstance(OpenAdminProfile.this).addToRequestQueue(postRequest4);

        StringRequest postRequest1 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(OpenAdminProfile.this,s,Toast.LENGTH_SHORT).show();
                if(s.equals("0")) {
                    sendJoinRequest.setEnabled(true);
                }
                else {
                    sendJoinRequest.setEnabled(false);
                    sendJoinRequest.setText("You have already send request !!!");
                    sendJoinRequest.setBackgroundColor(Color.parseColor("#567890"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(OpenAdminProfile.this,"Join Request Check Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("admin_name",sharedpreferences.getString(MainActivity.AdminName,""));
                params.put("follower_name",sharedpreferences.getString(MainActivity.UserName,""));
                return params;
            }
        };
        AppController.getInstance(OpenAdminProfile.this).addToRequestQueue(postRequest1);

        StringRequest postRequest2 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(OpenAdminProfile.this,s,Toast.LENGTH_SHORT).show();
                String[] adminDetails = s.split(",");
                admin_name.setText(adminDetails[0]);
                admin_email.setText(adminDetails[1]);
//                sendJoinRequest.setEnabled(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(OpenAdminProfile.this,"Admin Details Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("admin_name",sharedpreferences.getString(MainActivity.AdminName,""));

                return params;
            }
        };
        AppController.getInstance(OpenAdminProfile.this).addToRequestQueue(postRequest2);

        sendJoinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  s) {
                        //Toast.makeText(OpenAdminProfile.this,"Request Send Successfully !!!",Toast.LENGTH_SHORT).show();
                        sendJoinRequest.setText("Request Send");
                        sendJoinRequest.setBackgroundColor(Color.parseColor("#236790"));
                        sendJoinRequest.setEnabled(true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(OpenAdminProfile.this,"Join Request Failed",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("admin_name", sharedpreferences.getString(MainActivity.AdminName,""));
                        params.put("follower_name",sharedpreferences.getString(MainActivity.UserName,""));

                        return params;
                    }
                };
                AppController.getInstance(OpenAdminProfile.this).addToRequestQueue(postRequest);
            }
        });


    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ShowAdminsActivity.class));
    }
}
