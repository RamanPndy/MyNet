package com.example.ramanpandey.mynetdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminProfileActivity extends AppCompatActivity {
    TextView profile_user_name;
    Button Broadcast_Btn,logout_Btn,Show_Prev_Msg_Btn,Show_Followers,Join_Request_Btn;
    String url1 = "http://10.0.2.2/mynet/count_total_join_requests.php";
    String url2 = "http://10.0.2.2/mynet/count_none_following_request.php";
    SharedPreferences sharedpreferences;
    public static String msgCounter = "0";
    public static String requestCounter = "0";
    public static String noneRequestCounter = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);
        profile_user_name = (TextView)findViewById(R.id.admin_profile_username);
        Broadcast_Btn = (Button)findViewById(R.id.broadcast);
        logout_Btn = (Button)findViewById(R.id.admin_logout_button);
        Show_Prev_Msg_Btn = (Button)findViewById(R.id.showPrevMessageButton);
        Show_Followers = (Button)findViewById(R.id.showFollowers);
        Join_Request_Btn = (Button)findViewById(R.id.joinRequestMsgButton);
//        Join_Request_Btn.setText("New Request ( "+requestCounter+" )");
        Join_Request_Btn.setText("New Requests");


        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        profile_user_name.setText(sharedpreferences.getString(MainActivity.UserName, ""));

        StringRequest postRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(AdminProfileActivity.this,s,Toast.LENGTH_SHORT).show();


//                Join_Request_Btn.setText("New Request ( "+s+" )");
//                if(!s.equals("0"))
//
//                    Join_Request_Btn.setBackgroundColor(Color.RED);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AdminProfileActivity.this,"Join Request Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("admin_name", MainActivity.user_name);

                return params;
            }
        };
        AppController.getInstance(AdminProfileActivity.this).addToRequestQueue(postRequest);
        logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AdminProfileActivity.this, MainActivity.user_name + " Has Been Logged Out!!!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(MainActivity.UserName, "");
                editor.commit();
                Intent goToLoginActivity = new Intent(AdminProfileActivity.this, MainActivity.class);
                startActivity(goToLoginActivity);
            }
        });

        Broadcast_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToBroadcastAcvtivity = new Intent(AdminProfileActivity.this,BroadcastMessage.class);
                startActivity(goToBroadcastAcvtivity);
            }
        });

        Show_Prev_Msg_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToShowPrevMsgsActivity = new Intent(AdminProfileActivity.this,ShowPrevMessagesActivity.class);
                startActivity(goToShowPrevMsgsActivity);
            }
        });

        Show_Followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllFollowersActivity = new Intent(AdminProfileActivity.this,ShowFollowersActivity.class);
                startActivity(goToAllFollowersActivity);
            }
        });

        Join_Request_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFriendRequestActivity = new Intent(AdminProfileActivity.this,FriendRequests.class);
                startActivity(goToFriendRequestActivity);
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
