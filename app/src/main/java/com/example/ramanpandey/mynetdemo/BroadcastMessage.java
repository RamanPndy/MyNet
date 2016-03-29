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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BroadcastMessage extends AppCompatActivity {

    Button Post_Btn;
    EditText broadcast_msg,broadcast_subject;
    String url = "http://10.0.2.2/mynet/broadcast_msg_insert.php";
    String BroadCastMsg,BroasCastSubject;
    SharedPreferences sharedpreferences;
    public static int subjectId=0,messageId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_message);
        broadcast_msg = (EditText)findViewById(R.id.broadcast_msg);
        broadcast_subject = (EditText)findViewById(R.id.broadcastSubject);
        Post_Btn = (Button)findViewById(R.id.post_button);
        Post_Btn.setEnabled(false);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        broadcast_subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ++subjectId;
                if((messageId>0) && (subjectId>0))
                    Post_Btn.setEnabled(true);
            }
        });
        broadcast_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                ++messageId;
                if((messageId>0) && (subjectId>0))
                 Post_Btn.setEnabled(true);
            }
        });
        Post_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BroadCastMsg = broadcast_msg.getText().toString();
                BroasCastSubject = broadcast_subject.getText().toString();
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  s) {
                        if(s.equals("{\"success\":1}")) {

                            Intent goToWebResultActivity = new Intent(BroadcastMessage.this, WebResults.class);
                            startActivity(goToWebResultActivity);

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(BroadcastMessage.this,"Broadcast message insertion Failed",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_name", MainActivity.user_name);
                        params.put("broadcast_msg", BroadCastMsg);
                        params.put("broadcast_subject", BroasCastSubject);
                        return params;
                    }
                };
                AppController.getInstance(BroadcastMessage.this).addToRequestQueue(postRequest);
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminProfileActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
