package com.example.sudokusolver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 2500; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView appTitle = findViewById(R.id.appTitle);


        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        appTitle.startAnimation(fade);
        appTitle.startAnimation(scale);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME);
    }
}