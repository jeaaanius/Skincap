package com.example.skincap.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBoardingScreen = getSharedPreferences("onBoarding Screen",MODE_PRIVATE);
        boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

        if(isFirstTime){
            SharedPreferences.Editor editor = onBoardingScreen.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();
            startActivity(new Intent(this, OnBoarding.class));
        }
        else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}