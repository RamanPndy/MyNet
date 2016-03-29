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

public class ChkAdminPrivlage extends AppCompatActivity {

    String url = "http://10.0.2.2/mynet/chk_admin_privilage.php";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chk_admin_privlage);

        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES,MODE_PRIVATE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
               // Toast.makeText(ChkAdminPrivlage.this,s,Toast.LENGTH_SHORT).show();
                if(s.equals("0"))
                {
                    Intent goToUserProfileActivity = new Intent(ChkAdminPrivlage.this,UserProfileActivity.class);
                    startActivity(goToUserProfileActivity);
                }
                else
                {
                    Intent goToAdminProfileActivity = new Intent(ChkAdminPrivlage.this,AdminProfileActivity.class);
                    startActivity(goToAdminProfileActivity);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ChkAdminPrivlage.this,"Admin Privilage Test Failed",Toast.LENGTH_SHORT).show();
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
        AppController.getInstance(ChkAdminPrivlage.this).addToRequestQueue(postRequest);

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
