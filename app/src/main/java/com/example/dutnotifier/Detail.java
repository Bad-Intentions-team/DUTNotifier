package com.example.dutnotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {
    public TextView tvTitle,tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle =findViewById(R.id.tv_title);
        tvContent=findViewById(R.id.tv_content);
        Intent intent =getIntent();
        String title =intent.getStringExtra("TITLE");
        String content =intent.getStringExtra("CONTENT");
        tvTitle.setText(title);
        tvContent.setText(content);
    }
}
