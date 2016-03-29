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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button loginbtn;
    public static String user_name,pass_word;
    String url = "http://10.0.2.2/mynet/chkdata.php";
    TextView register_textView;
    public static int userId=0,PassId=0;
    public static final String MyPREFERENCES = "AppPrefs" ;
    public static final String UserName = "userNameKey";
    public static final String BroadCastSubject = "broadcastSubjectKey";
    public static final String AdminName = "adminNameKey";
    public static final String FriendName = "friendNameKey";
    public static final String GETAdminName = "getAdminNameKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.UserName);
        password = (EditText)findViewById(R.id.Password);
        register_textView = (TextView)findViewById(R.id.register_text_view);
        loginbtn = (Button)findViewById(R.id.LoginButton);
        loginbtn.setEnabled(false);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        register_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(MainActivity.this,RegisterUser.class);
                startActivity(registerActivity);
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ++userId;
                if ((userId > 0) && (PassId > 0))
                    loginbtn.setEnabled(true);
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ++PassId;
                if ((userId > 0) && (PassId > 0))
                    loginbtn.setEnabled(true);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = username.getText().toString();
                pass_word = password.getText().toString();
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("{\"success\":1}")) {
                            Toast.makeText(MainActivity.this, "Data Exists in DB", Toast.LENGTH_SHORT).show();
                            Intent showUsers = new Intent(MainActivity.this,LoginTest.class);
                            startActivity(showUsers);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Data Doesn't Exists in DB", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this,"Data Check failed",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_name", user_name);
                        return params;
                    }
                };
                AppController.getInstance(MainActivity.this).addToRequestQueue(postRequest);
            }
        });

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
