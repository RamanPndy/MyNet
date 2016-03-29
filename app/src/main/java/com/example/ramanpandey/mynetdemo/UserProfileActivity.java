package com.example.ramanpandey.mynetdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class UserProfileActivity extends AppCompatActivity {
    TextView profile_user_name;
    Button logout_Btn,Show_New_Msg_Btn,Show_Admins_Btn;
    String url2 = "http://10.0.2.2/mynet/count_total_unread_msg.php";
   // String url1 = "http://10.0.2.2/mynet/get_admin_names.php";
    SharedPreferences sharedpreferences;
    public static int i,msgCounter = 0;
    public List<Integer> messages;
    public List<String> admins;
    public String admin_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        profile_user_name = (TextView)findViewById(R.id.user_profile_username);
       
        logout_Btn = (Button)findViewById(R.id.user_logout_button);

        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        Show_New_Msg_Btn = (Button)findViewById(R.id.showNewMessage);
        Show_Admins_Btn = (Button)findViewById(R.id.showAdmins);

        messages = new ArrayList<Integer>();
        admins = new ArrayList<>();


        for (int i=0;i < admins.size();i++) {
            admin_name = admins.get(i);
            StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String  s) {
                    Toast.makeText(UserProfileActivity.this,s,Toast.LENGTH_SHORT).show();
                    messages.add(Integer.parseInt(s));
                    int totalMsgs = getSumOfElements(messages);
                    Show_New_Msg_Btn.setText("New Messages ( " +Integer.toString(totalMsgs) + " )");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(UserProfileActivity.this,"Total Unread Messages Fetching Failed",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("admin_name", admin_name);
                    return params;
                }
            };
            AppController.getInstance(UserProfileActivity.this).addToRequestQueue(postRequest2);
        }
//        msgCounter = messages.size();
//        Show_New_Msg_Btn.setText("New Messages ( " + Integer.toString(msgCounter) + " )");
//        StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String  s) {
//                Show_New_Msg_Btn.setText("New Messages ( " + s + " )");
//                if(Integer.parseInt(s) != 0)
//                    Show_New_Msg_Btn.setBackgroundColor(Color.RED);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(UserProfileActivity.this,"New Message Count Fetch Failed",Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_name", MainActivity.user_name);
//                return params;
//            }
//        };
//        AppController.getInstance(UserProfileActivity.this).addToRequestQueue(postRequest2);

        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);
        profile_user_name.setText(sharedpreferences.getString(MainActivity.UserName, ""));

        logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UserProfileActivity.this, MainActivity.user_name + " Has Been Logged Out!!!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(MainActivity.UserName, "");
                editor.commit();
                Intent goToLoginActivity = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(goToLoginActivity);
            }
        });

        

        Show_New_Msg_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToShowNewMessageActivity = new Intent(UserProfileActivity.this,ShowFollowingAdmins.class);
                startActivity(goToShowNewMessageActivity);
            }
        });

        Show_Admins_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToShowAllAdminsActivity = new Intent(UserProfileActivity.this,ShowAdminsActivity.class);
                startActivity(goToShowAllAdminsActivity);
            }
        });

    }

    public int getSumOfElements(List<Integer> messageList){
        int i;
        int sum = 0;
        for(i = 1; i < messageList.size(); i++)
            sum += messageList.get(i);
        return sum;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
