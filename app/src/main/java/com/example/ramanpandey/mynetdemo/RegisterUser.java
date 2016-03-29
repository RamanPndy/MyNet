package com.example.ramanpandey.mynetdemo;

import android.content.Intent;
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

public class RegisterUser extends AppCompatActivity {
    EditText username,name,email,password;
    Button registerbtn;
    public static String user_name,Name,Email,pass_word;
    String url = "http://10.0.2.2/mynet/insertdata.php";
    public static int userId=0,nameId=0,emailId=0,passId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        username = (EditText)findViewById(R.id.user_name_register);
        name = (EditText)findViewById(R.id.name_register);
        email = (EditText)findViewById(R.id.email_register);
        password = (EditText)findViewById(R.id.password_register);

        registerbtn = (Button)findViewById(R.id.register_user_btn);
        registerbtn.setEnabled(false);

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
                if ((userId > 0) && (passId > 0) && (emailId > 0) && (nameId > 0))
                    registerbtn.setEnabled(true);
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ++nameId;
                if ((userId > 0) && (passId > 0) && (emailId > 0) && (nameId > 0))
                    registerbtn.setEnabled(true);
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ++emailId;
                if ((userId > 0) && (passId > 0) && (emailId > 0) && (nameId > 0))
                    registerbtn.setEnabled(true);
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
                ++passId;
                if ((userId > 0) && (passId > 0) && (emailId > 0) && (nameId > 0))
                    registerbtn.setEnabled(true);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = username.getText().toString();
                Name = name.getText().toString();
                Email = email.getText().toString();
                pass_word = password.getText().toString();
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String  s) {
                        Toast.makeText(RegisterUser.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                        Intent loginActivity = new Intent(RegisterUser.this,MainActivity.class);
                        startActivity(loginActivity);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(RegisterUser.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_name", user_name);
                        params.put("name", Name);
                        params.put("email", Email);
                        params.put("password", pass_word);
                        return params;
                    }
                };
                AppController.getInstance(RegisterUser.this).addToRequestQueue(postRequest);
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
