package com.example.vandan_login_db;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class databaseactivity extends AppCompatActivity {

    EditText et_name,et_dept,et_update;
    Button btn_add,btn_view,btn_update,btn_del;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databaseactivity);

        et_name = findViewById(R.id.et_name_db);
        et_dept = findViewById(R.id.et_dept_db);
        et_update = findViewById(R.id.et_id_db);

        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        btn_del = findViewById(R.id.btn_dlt);
        btn_update = findViewById(R.id.btn_upd);

        sql = openOrCreateDatabase("vandan",MODE_PRIVATE,null);
        String create = "CREATE TABLE IF NOT EXISTS STUDENT(stu_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(100),dept VARCHAR(100) )";
        sql.execSQL(create);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                String dept = et_dept.getText().toString();

                String insert = "INSERT INTO STUDENT(name,dept) VALUES (\""+name+"\", \""+dept+"\" )";
                sql.execSQL(insert);
                Toast.makeText(databaseactivity.this,"Data inserted",Toast.LENGTH_LONG).show();
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getQuery = "SELECT * FROM STUDENT";
                Cursor cursor = sql.rawQuery(getQuery,null);
                StringBuffer str = new StringBuffer();
                if (cursor.moveToFirst()){
                    do {
                        str.append("ID   : " + cursor.getString(0) + "\n");
                        str.append("Name : " + cursor.getString(1) + "\n");
                        str.append("Dept : " + cursor.getString(2) + "\n");
                    }while (cursor.moveToNext());
                }
                cursor.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(databaseactivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data: ");
                builder.setMessage(str);
                builder.show();

            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_update.getText().toString();
                String del = "DELETE FROM STUDENT WHERE stu_id = \"" + id + "\"";
                sql.execSQL(del);
                Toast.makeText(databaseactivity.this,"1 item Deleted",Toast.LENGTH_LONG).show();

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_update.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("id",id);

                Intent i = new Intent(databaseactivity.this,update.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

    }
}
