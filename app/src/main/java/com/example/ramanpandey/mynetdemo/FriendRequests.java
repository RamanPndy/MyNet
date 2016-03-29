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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FriendRequests extends AppCompatActivity {
    TextView noFriendRequestTextView;
    String url = "http://10.0.2.2/mynet/show_friend_requests.php";
    public ListView show_friend_requests;
    public ArrayAdapter<String> msgsListAdapter;
    public List<String> msgList = new ArrayList<String>();
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_requests);
        show_friend_requests = (ListView)findViewById(R.id.friendRequestlistView);
        noFriendRequestTextView = (TextView)findViewById(R.id.friendRequesttextView);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(FriendRequests.this,s,Toast.LENGTH_SHORT).show();
//                if(s){
//                    show_friend_requests.setVisibility(View.INVISIBLE);
//                    Toast.makeText(FriendRequests.this,"There are No Friend Request",Toast.LENGTH_SHORT).show();
//                    Intent goToAdminProfileActivity = new Intent(FriendRequests.this,ChkAdminPrivlage.class);
//                    startActivity(goToAdminProfileActivity);
//                }
                String responseStr = s.replace(",none","");
                if((!responseStr.equals(""))) {
                    noFriendRequestTextView.setText("");
                    String[] adminsStr = responseStr.split(",");
                    String[] admins = Arrays.copyOfRange(adminsStr, 1, adminsStr.length);
                    String[] adminsArray = new HashSet<String>(Arrays.asList(admins)).toArray(new String[0]);
                    msgsListAdapter = new ArrayAdapter<String>(FriendRequests.this, android.R.layout.simple_list_item_checked, adminsArray);
                    show_friend_requests.setAdapter(msgsListAdapter);

                    show_friend_requests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selected_friend_name = show_friend_requests.getItemAtPosition(position).toString();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(MainActivity.FriendName, selected_friend_name);
                            editor.commit();
                            Intent goToOpenUserProfileActivity = new Intent(FriendRequests.this, OpenUserProfile.class);
                            startActivity(goToOpenUserProfileActivity);
                        }
                    });
                }
                else{
                    noFriendRequestTextView.setText("There are No Friend Requests Yet!!!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FriendRequests.this,"Admins Fetching Failed",Toast.LENGTH_SHORT).show();
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
        AppController.getInstance(FriendRequests.this).addToRequestQueue(postRequest);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
