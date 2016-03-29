package com.example.ramanpandey.mynetdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class ShowFollowingAdmins extends AppCompatActivity {
    TextView noFollowingAdminsTextView;
    String url = "http://10.0.2.2/mynet/show_all_followings.php";
    public ListView followings_admins;
    public ArrayAdapter<String> followingAdminsListAdapter;
    public List<String> followingAdminsList = new ArrayList<String>();
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_following_admins);
        followings_admins = (ListView)findViewById(R.id.followingAdminslistView);
        noFollowingAdminsTextView = (TextView)findViewById(R.id.noFollowingAdminstextView);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(ShowFollowingAdmins.this,s,Toast.LENGTH_SHORT).show();
                if(!s.equals("")) {
                    String[] followingAdminsStr = s.split(",");
                    String[] followingAdmins = Arrays.copyOfRange(followingAdminsStr, 1, followingAdminsStr.length);
                    followingAdminsListAdapter = new ArrayAdapter<String>(ShowFollowingAdmins.this, android.R.layout.simple_list_item_checked, followingAdmins);
                    followings_admins.setAdapter(followingAdminsListAdapter);

                    followings_admins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selected_following_admin_name = followings_admins.getItemAtPosition(position).toString();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(MainActivity.AdminName, selected_following_admin_name);
                            editor.commit();
                            Intent goToShowNewMessageActivity = new Intent(ShowFollowingAdmins.this, ShowNewMessageActivity.class);
                            startActivity(goToShowNewMessageActivity);
                        }
                    });
                }
                else{
                    noFollowingAdminsTextView.setText("There are No Following Admins to Show !!!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ShowFollowingAdmins.this,"Following Admins Fetching Failed",Toast.LENGTH_SHORT).show();
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
        AppController.getInstance(ShowFollowingAdmins.this).addToRequestQueue(postRequest2);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
