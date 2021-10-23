package com.example.skincap.ui.journal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.skincap.databinding.ActivityCreateJournalBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.security.cert.CertPathBuilder;
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
        binding.cancel.setOnClickListener(e -> finish());
    }

    private void setSelectedDate(View button) {

        // Makes only dates from today forward selectable.
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.show(getSupportFragmentManager(), "DatePicker");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            ((MaterialButton) button).setText(datePicker.getHeaderText());
        });
    }

    private void setSelectedTime(View button) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .build();

        timePicker.show(getSupportFragmentManager(), "TimePicker");
        timePicker.addOnPositiveButtonClickListener(selection -> {
            //((MaterialButton) button).setText(String.format("%02d", hour), String.format("%02d", minute));
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}