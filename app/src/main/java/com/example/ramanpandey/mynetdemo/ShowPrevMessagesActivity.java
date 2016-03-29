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

public class ShowPrevMessagesActivity extends AppCompatActivity {
    String url = "http://10.0.2.2/mynet/show_prev_msgs.php";
    TextView noPrevMsgTextView;
    public ListView show_all_prev_msgs;
    public ArrayAdapter<String> msgsListAdapter;
    public List<String> msgList;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_prev_msgs);
        show_all_prev_msgs = (ListView)findViewById(R.id.prev_msg_list);
        noPrevMsgTextView = (TextView)findViewById(R.id.NoPrevMessages);
        msgList = new ArrayList<String>();
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                if(!s.equals("")) {
                    //Toast.makeText(ShowPrevMessagesActivity.this,s,Toast.LENGTH_SHORT).show();
                    String[] msgStr = s.split(",");
                    String[] msgs = Arrays.copyOfRange(msgStr, 1, msgStr.length);
                    msgsListAdapter = new ArrayAdapter<String>(ShowPrevMessagesActivity.this, android.R.layout.simple_list_item_checked, msgs);
                    show_all_prev_msgs.setAdapter(msgsListAdapter);

                    show_all_prev_msgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selected_msg_subject = show_all_prev_msgs.getItemAtPosition(position).toString();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(MainActivity.BroadCastSubject, selected_msg_subject);
                            editor.commit();
                            Intent goToFullMessageActivity = new Intent(ShowPrevMessagesActivity.this, FullMessageActivity.class);
                            startActivity(goToFullMessageActivity);
                        }
                    });
                }else
                {
                    noPrevMsgTextView.setText("There are No Messages Yet!!!");
                }

//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ShowPrevMessagesActivity.this,"Broadcast message Fetching Failed",Toast.LENGTH_SHORT).show();
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
        AppController.getInstance(ShowPrevMessagesActivity.this).addToRequestQueue(postRequest);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminProfileActivity.class));
    }
}
