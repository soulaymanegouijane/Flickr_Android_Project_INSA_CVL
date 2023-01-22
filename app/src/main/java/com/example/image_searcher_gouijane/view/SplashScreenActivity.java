package com.example.image_searcher_gouijane.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.image_searcher_gouijane.MainActivity;
import com.example.image_searcher_gouijane.R;

import java.util.zip.Inflater;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
@Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.splash_screen);
        super.onCreate(savedInstanceState);
    this.handler = new Handler();
    handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                finish();
                            }
                        },3000);
    }
}