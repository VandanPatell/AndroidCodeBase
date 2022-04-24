package com.example.vandan_login_db;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class update extends AppCompatActivity {

    TextView tv_update_id;
    EditText et_update_name,et_update_dept;
    Button btn_update_data, btn_update_back;

    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        sql = openOrCreateDatabase("vandan",MODE_PRIVATE,null);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        tv_update_id = findViewById(R.id.tv_update_id);
        tv_update_id.setText(id);

        et_update_name = findViewById(R.id.et_update_name);
        et_update_dept = findViewById(R.id.et_update_dept);
        btn_update_data = findViewById(R.id.btn_update_data);
        btn_update_back = findViewById(R.id.btn_update_back);

        String getQuery = "SELECT * FROM STUDENT WHERE stu_id = \"" + id + "\"";

        Cursor cursor = sql.rawQuery(getQuery,null);
        StringBuffer str = new StringBuffer();

        while(cursor.moveToNext()){
            et_update_name.setText(cursor.getString(1));
            et_update_dept.setText(cursor.getString(2));
        }
        cursor.close();

        btn_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_update_name.getText().toString();
                String dept = et_update_dept.getText().toString();

                String update = "UPDATE STUDENT SET name = \"" +name + "\", dept=\""+dept+"\" WHERE stu_id = \""+id+"\"" ;
                sql.execSQL(update);
                Toast.makeText(update.this,"1 record updated.",Toast.LENGTH_LONG).show();

            }
        });

        btn_update_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(update.this,databaseactivity.class);
                startActivity(i);

            }
        });


    }

}
