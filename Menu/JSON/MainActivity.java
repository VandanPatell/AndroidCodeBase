package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lv_listvew;

    String jsonContact = "\"Students\":[\"name\":\"vandan\",\"marks\":\"20\"]";
    ArrayList<HashMap<String,String>> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_listvew = findViewById(R.id.lv_student_list);

        try {
            JSONObject jsonObject = new JSONObject(jsonContact);
            JSONArray jsonArray = jsonObject.getJSONArray("Students");

            for (int i=0; i<jsonArray.length(); i++){
                HashMap<String,String> stu = new HashMap<>();
                JSONObject jsobj = jsonArray.getJSONObject(i);

                stu.put("name",jsobj.getString("name"));
                stu.put("marks",jsobj.getString("marks"));
                userList.add(stu);

            }

            lv_listvew.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return userList.size();
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item,null);

                    TextView name = findViewById(R.id.tv_name);
                    TextView marks = findViewById(R.id.tv_marks);

                    name.setText(userList.get(i).get("name"));
                    marks.setText(userList.get(i).get("marks"));

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
        getMenuInflater().inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Intent i;
        switch (item.getItemId()){
            case R.id.Call:
                tvNumber =
                        (TextView) findViewById(R.id.tvNumber);
                i = new Intent(Intent.ACTION_CALL);
                tvs=findViewById(R.id.tvStudentName);
                Log.d("ff",tvs.getText().toString());
                i.setData(Uri.parse("tel: " +userList.get(pos).get("Number")));
                startActivity(i);
//Toast.makeText(getApplicationContext(),tvNumber.getText().toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.Dial:
                tvNumber =
                        (TextView) findViewById(R.id.tvNumber);
                i = new Intent(Intent.ACTION_DIAL);
                tvs=findViewById(R.id.tvStudentName);
                Log.d("ff",tvs.getText().toString());
                i.setData(Uri.parse("tel: " +userList.get(pos).get("Number")));
                startActivity(i);
                break;
        }
        
        return super.onContextItemSelected(item);
    }
}
