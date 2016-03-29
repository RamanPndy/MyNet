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

public class OpenUserProfile extends AppCompatActivity {
    TextView user_user_name,user_profile_name,user_profile_email;
    String url1 = "http://10.0.2.2/mynet/get_user_profile.php";
    String url2 = "http://10.0.2.2/mynet/accept_join_request.php";
    String url3 = "http://10.0.2.2/mynet/reject_join_request.php";
    Button acceptJoinRequest,rejectJoinRequest;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_response);
        user_user_name = (TextView)findViewById(R.id.userProfileUserName);
        user_profile_name = (TextView)findViewById(R.id.userProfileName);
        user_profile_email = (TextView)findViewById(R.id.userProfileUserEmail);
        acceptJoinRequest = (Button)findViewById(R.id.requestAcceptButton);
        rejectJoinRequest = (Button)findViewById(R.id.requestRejectButton);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(OpenUserProfile.this,s,Toast.LENGTH_SHORT).show();
                String[] adminDetails = s.split(",");
                user_user_name.setText(adminDetails[0]);
                user_profile_name.setText(adminDetails[1]);
                user_profile_email.setText(adminDetails[2]);
//                sendJoinRequest.setEnabled(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(OpenUserProfile.this,"User Details Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name",sharedpreferences.getString(MainActivity.FriendName,""));

                return params;
            }
        };
        AppController.getInstance(OpenUserProfile.this).addToRequestQueue(postRequest1);

        acceptJoinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(OpenUserProfile.this, "Request Accepted !!!", Toast.LENGTH_SHORT).show();
                        Intent goToAdminProfileActivity = new Intent(OpenUserProfile.this,ChkAdminPrivlage.class);
                        startActivity(goToAdminProfileActivity);
//                        acceptJoinRequest.setVisibility(View.INVISIBLE);
//                        rejectJoinRequest.setVisibility(View.INVISIBLE);
//                sendJoinRequest.setEnabled(true);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(OpenUserProfile.this, "User Request Accept Failed", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("admin_name", sharedpreferences.getString(MainActivity.UserName, ""));
                        params.put("follower_name", user_user_name.getText().toString());
                        return params;
                    }
                };
                AppController.getInstance(OpenUserProfile.this).addToRequestQueue(postRequest2);


            }
        });

        rejectJoinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest3 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(OpenUserProfile.this, "Request Rejected", Toast.LENGTH_SHORT).show();
                        Intent goToAdminProfileActivity = new Intent(OpenUserProfile.this,ChkAdminPrivlage.class);
                        startActivity(goToAdminProfileActivity);
//                        acceptJoinRequest.setVisibility(View.INVISIBLE);
//                        rejectJoinRequest.setVisibility(View.INVISIBLE);
//                sendJoinRequest.setEnabled(true);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(OpenUserProfile.this, "User Request Reject Failed", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("admin_name", sharedpreferences.getString(MainActivity.UserName, ""));
                        params.put("follower_name", user_user_name.getText().toString());
                        return params;
                    }
                };
                AppController.getInstance(OpenUserProfile.this).addToRequestQueue(postRequest3);
            }
        });



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FriendRequests.class));
    }
}
