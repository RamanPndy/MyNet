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

public class ShowNewMessageActivity extends AppCompatActivity {
    TextView noMsgsTextView;
    String url2 = "http://10.0.2.2/mynet/show_new_msgs.php";
    public ListView show_new_msgs;
    public ArrayAdapter<String> msgsListAdapter;
    public List<String> msgList = new ArrayList<String>();
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_new_messages);
        show_new_msgs = (ListView)findViewById(R.id.showNewMessages);
        noMsgsTextView = (TextView)findViewById(R.id.noMsgstextView);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(ShowNewMessageActivity.this,s,Toast.LENGTH_SHORT).show();
                if(!s.equals("")) {
                    String[] msgStr = s.split(",");
                    String[] msgs = Arrays.copyOfRange(msgStr, 1, msgStr.length);
                    msgsListAdapter = new ArrayAdapter<String>(ShowNewMessageActivity.this, android.R.layout.simple_list_item_checked, msgs);
                    show_new_msgs.setAdapter(msgsListAdapter);

                    show_new_msgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selected_msg_subject = show_new_msgs.getItemAtPosition(position).toString();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(MainActivity.BroadCastSubject, selected_msg_subject);
                            editor.commit();
                            Intent goToOpenNewMessageActivity = new Intent(ShowNewMessageActivity.this, OpenNewMessage.class);
                            startActivity(goToOpenNewMessageActivity);
                        }
                    });
                }
                else{
                    noMsgsTextView.setText("There are No Messages to Show !!!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ShowNewMessageActivity.this,"Broadcast message Fetching Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", sharedpreferences.getString(MainActivity.AdminName,""));

                return params;
            }
        };
        AppController.getInstance(ShowNewMessageActivity.this).addToRequestQueue(postRequest2);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
