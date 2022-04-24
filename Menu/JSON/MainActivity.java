package com.example.vandan_login_db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class jsonactivity extends AppCompatActivity {

    TextView tv_username,tv_password;
    ListView lv_students;
    int pos=0;

    String JsonData = "{ \"Students\" : [ " +
            "{ \"name\" : \"vandan\" , \"contact\" : \"9408251428\" }, " +
            "{ \"name\" : \"tony\" , \"contact\" : \"9408251428\" } " +
            "]}";

    ArrayList<HashMap<String,String>> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonactivity);

        tv_username = findViewById(R.id.tv_username_json);
        tv_password = findViewById(R.id.tv_password_json);
        lv_students = findViewById(R.id.lv_studentslist);

        registerForContextMenu(lv_students);

//        Fetch SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","null");
        String password = sharedPreferences.getString("password","null");

        tv_username.setText("username: " + username);
        tv_password.setText("password: " +password);

        try {
            JSONObject jsonObject = new JSONObject(JsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("Students");

            for (int i=0; i<jsonArray.length(); i++){
                HashMap<String, String> usl = new HashMap<>();
                JSONObject jsobj = jsonArray.getJSONObject(i);

                usl.put("name",jsobj.getString("name"));
                usl.put("contact",jsobj.getString("contact"));
                Log.d("---------------------",usl.toString());
                userList.add(usl);
            }

            lv_students.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return userList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    pos = position;
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.list_items_json,null);

                    TextView tv_name = ll.findViewById(R.id.tv_name);
                    TextView tv_contact = ll.findViewById(R.id.tv_contact);

                    tv_name.setText(userList.get(position).get("name"));
                    tv_contact.setText(userList.get(position).get("contact"));

                    return ll;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_call_json,menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Intent i;

        switch (item.getItemId()){
            case R.id.menu_call:
                i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel: "+ userList.get(pos).get("contact")));
                startActivity(i);
                break;

            case R.id.menu_dial:
                i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel: "+userList.get(pos).get("contact")));
                startActivity(i);
                break;

            case R.id.menu_sms:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("smsto: "));
                i.putExtra("address",userList.get(pos).get("contact"));
                i.putExtra("sms_body","Hello world");
                startActivity(i);
                break;

            case R.id.menu_site:
                i= new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.google.com"));
                startActivity(i);
                break;

        }

        return super.onContextItemSelected(item);
    }
}
