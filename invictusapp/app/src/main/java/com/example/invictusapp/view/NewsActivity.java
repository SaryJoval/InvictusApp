package com.example.invictusapp.view;

import androidx.appcompat.app.AppCompatActivity;

import com.example.invictusapp.MainActivity;
import com.example.invictusapp.R;
import com.example.invictusapp.WelcomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView  textView2;
    private ImageView imageView2;
    private TextView textView3;
    private ImageView imageView3;
    private TextView textView4;
    private Button btnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
