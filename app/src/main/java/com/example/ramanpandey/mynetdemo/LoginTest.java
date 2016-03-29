package com.example.ramanpandey.mynetdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginTest extends AppCompatActivity {
    TextView login_test_failed,try_again;
    String url = "http://10.0.2.2/mynet/logintest.php";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_test);
        login_test_failed = (TextView)findViewById(R.id.loginTestFailed);
        try_again = (TextView)findViewById(R.id.try_again);
        try_again.setVisibility(View.INVISIBLE);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES,MODE_PRIVATE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                if(s.equals("{\"success\":1}")) {
                    Toast.makeText(LoginTest.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MainActivity.UserName,MainActivity.user_name);
                    editor.commit();

                    Intent goToChkAdminPrivlageActivity = new Intent(LoginTest.this, ChkAdminPrivlage.class);
                    startActivity(goToChkAdminPrivlageActivity);

                }
                else {
                    login_test_failed.setText("Username and Password Doesn't Valid!!!");
                    try_again.setVisibility(View.VISIBLE);
                    try_again.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent goToLoginActivity = new Intent(LoginTest.this,MainActivity.class);
                            startActivity(goToLoginActivity);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginTest.this,"Login Test Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", MainActivity.user_name);
                params.put("pass_word", MainActivity.pass_word);
                return params;
            }
        };
        AppController.getInstance(LoginTest.this).addToRequestQueue(postRequest);

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
