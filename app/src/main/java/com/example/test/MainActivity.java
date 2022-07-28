package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv_id, tv_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPW = intent.getStringExtra("userPW");

        tv_id.setText(userID);
        tv_pw.setText(userPW);
    }
}
