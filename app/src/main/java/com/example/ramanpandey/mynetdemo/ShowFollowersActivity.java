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

public class ShowFollowersActivity extends AppCompatActivity {
    TextView noFollowersTextView;
    String url = "http://10.0.2.2/mynet/show_all_followers.php";
    public ListView show_all_followers;
    public ArrayAdapter<String> msgsListAdapter;
    public List<String> msgList = new ArrayList<String>();
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_followers);
        show_all_followers = (ListView)findViewById(R.id.followersList);
        noFollowersTextView = (TextView)findViewById(R.id.noFollowerstextView);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(ShowFollowersActivity.this,s,Toast.LENGTH_SHORT).show();
                if(!s.equals("")) {
                    noFollowersTextView.setText("");
                    String[] followersStr = s.split(",");
                    String[] followers = Arrays.copyOfRange(followersStr, 1, followersStr.length);
                    msgsListAdapter = new ArrayAdapter<String>(ShowFollowersActivity.this, android.R.layout.simple_list_item_checked, followers);
                    show_all_followers.setAdapter(msgsListAdapter);

//                    show_all_followers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            String selected_msg_subject = show_all_followers.getItemAtPosition(position).toString();
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString(MainActivity.BroadCastSubject, selected_msg_subject);
//                            editor.commit();
//                            Intent goToOpenNewMessageActivity = new Intent(ShowFollowersActivity.this, OpenNewMessage.class);
//                            startActivity(goToOpenNewMessageActivity);
//                        }
//                    });
                }
                else{
                    noFollowersTextView.setText("There are No Followers Yet!!!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ShowFollowersActivity.this,"Followers Fetching Failed",Toast.LENGTH_SHORT).show();
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
        AppController.getInstance(ShowFollowersActivity.this).addToRequestQueue(postRequest);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
