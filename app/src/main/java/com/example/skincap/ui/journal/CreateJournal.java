package com.example.skincap.ui.journal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skincap.R;

public class CreateJournal extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_journal);


    }

    public void cancel_button(View view) {
        this.finish();
    }

    public void save_button(View view) {

    }

}
