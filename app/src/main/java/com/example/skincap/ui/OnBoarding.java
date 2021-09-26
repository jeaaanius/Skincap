package com.example.skincap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.skincap.R;

public class OnBoarding extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;
    ImageView[] dots;
    Button getStarted;
    Button skipBtn;
    Button nextBtn;
    Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        getStarted = findViewById(R.id.get_started_btn);
        skipBtn = findViewById(R.id.skip_btn);
        nextBtn = findViewById(R.id.next_btn);

        //Call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        //Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPos + 1);

    }

    private void addDots(int position){

        dots = new ImageView[4];
        dotsLayout.removeAllViews();

        for(int i=0; i<dots.length;i++){
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.circle_indicator_unselected);
            dots[i].setPadding(20,20,20,20);

            dotsLayout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setImageResource(R.drawable.circle_inidcator_selected);
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            currentPos = position;

            if(position == 0) {
                getStarted.setVisibility(View.INVISIBLE);
                skipBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            }
            else if(position == 1) {
                getStarted.setVisibility(View.INVISIBLE);
                skipBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            }
            else if(position == 2) {
                getStarted.setVisibility(View.INVISIBLE);
                skipBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            }
            else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.bottom_anim);
                getStarted.setAnimation(animation);
                getStarted.setVisibility(View.VISIBLE);
                skipBtn.setVisibility(View.INVISIBLE);
                nextBtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}