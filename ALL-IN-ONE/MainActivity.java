package com.example.vandan_login_db;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText et_username, et_password;
    Button btn_login,btn_database;

    Spinner spinner;
    Button calender_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_database = findViewById(R.id.btn_Database);
        calender_btn=findViewById(R.id.calender);


        calender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this);
                    datePickerDialog.setOnDateSetListener(MainActivity.this);
                    datePickerDialog.show();
                }
            }
        });


        spinner=findViewById(R.id.sp_choices);
        ArrayAdapter adp=new ArrayAdapter(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.user_choices));
        spinner.setAdapter(adp);
//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if(username.equals("admin") & password.equals("123") ){
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.apply();

                    Intent i = new Intent(MainActivity.this,jsonactivity.class);
                    startActivity(i);


                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Login Failed");
                    builder.setMessage("username or password incorrect");
                    builder.show();
                }
            }
        });

        btn_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,databaseactivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Log.d("date", String.valueOf(month));
    }
}
