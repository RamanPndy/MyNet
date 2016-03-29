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

public class ShowAdminsActivity extends AppCompatActivity {
    TextView noAdminsTextView;
    String url = "http://10.0.2.2/mynet/show_all_admins.php";
    public ListView show_all_admins;
    public ArrayAdapter<String> msgsListAdapter;
    public List<String> msgList = new ArrayList<String>();
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_admins);
        show_all_admins = (ListView)findViewById(R.id.showAdminslistView);
        noAdminsTextView = (TextView)findViewById(R.id.noAdminstextView);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  s) {
                //Toast.makeText(ShowAdminsActivity.this,s,Toast.LENGTH_SHORT).show();
                if(!s.equals("")) {
                    noAdminsTextView.setText("");
                    String[] adminsStr = s.split(",");
                    String[] admins = Arrays.copyOfRange(adminsStr, 1, adminsStr.length);
                    msgsListAdapter = new ArrayAdapter<String>(ShowAdminsActivity.this, android.R.layout.simple_list_item_checked, admins);
                    show_all_admins.setAdapter(msgsListAdapter);

                    show_all_admins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selected_admin_name = show_all_admins.getItemAtPosition(position).toString();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(MainActivity.AdminName, selected_admin_name);
                            editor.commit();
                            Intent goToOpenAdminProfileActivity = new Intent(ShowAdminsActivity.this, OpenAdminProfile.class);
                            startActivity(goToOpenAdminProfileActivity);
                        }
                    });
                }
                else{
                    noAdminsTextView.setText("There are No Admins Yet!!!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ShowAdminsActivity.this,"Admins Fetching Failed",Toast.LENGTH_SHORT).show();
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
        AppController.getInstance(ShowAdminsActivity.this).addToRequestQueue(postRequest);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChkAdminPrivlage.class));
    }
}
