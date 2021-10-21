package com.example.skincap.ui.journal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.skincap.databinding.ActivityCreateJournalBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.TimeZone;

public class CreateJournalActivity extends AppCompatActivity {

    private ActivityCreateJournalBinding binding;

    private CreateJournalViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CreateJournalViewModel.class);

        binding = ActivityCreateJournalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    private void setListeners() {
        binding.startDateButton.setOnClickListener(this::setSelectedDate);
        binding.expectDateButton.setOnClickListener(this::setSelectedDate);
        binding.timeNotifButton.setOnClickListener(this::setSelectedTime);
        binding.save.setOnClickListener(e -> finish());
    }

    private void setSelectedDate(View button) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.show(getSupportFragmentManager(), "DatePicker");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            ((MaterialButton) button).setText(String.valueOf(calendar.getTimeInMillis()));
        });
    }

    private void setSelectedTime(View button) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();

        timePicker.show(getSupportFragmentManager(), "TimePicker");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}