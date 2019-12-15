package com.example.dutnotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView mTvTitle, mTvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTvTitle =findViewById(R.id.tv_title);
        mTvContent =findViewById(R.id.tv_content);
        Intent intent =getIntent();
        String title =intent.getStringExtra("TITLE");
        String content =intent.getStringExtra("CONTENT");
        mTvTitle.setText(title);
        mTvContent.setText(content);
    }
}
