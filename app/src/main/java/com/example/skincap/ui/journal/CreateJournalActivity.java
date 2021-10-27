package com.example.skincap.ui.journal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.skincap.databinding.ActivityCreateJournalBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

public class CreateJournalActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;

    private ActivityCreateJournalBinding binding;

    private CreateJournalViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CreateJournalViewModel.class);

        binding = ActivityCreateJournalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setInputListeners();
        setListeners();
    }

    private void setListeners() {
        binding.expectDateButton.setOnClickListener(button ->
                setSelectedDate(button, selectedDate -> viewModel.setExpectedDueDate(selectedDate)));

        binding.startDateButton.setOnClickListener(button ->
                setSelectedDate(button, selectedDate -> viewModel.setStartDate(selectedDate)));

        binding.includePhotoButton.setOnClickListener(this::onClickIncludePhotoButton);

        binding.timeNotifButton.setOnClickListener(this::setSelectedTime);

        binding.cancel.setOnClickListener(e -> finish());

        binding.save.setOnClickListener(e -> {
            viewModel.addJournal();
            finish();
        });
    }

    private void onClickIncludePhotoButton(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            //  Gallery
            getSelectedImage(data);
        }

    }

    private void getSelectedImage(@Nullable final Intent data) {

        Uri selectedImage = data.getData();
        binding.includePhotoView.setImageURI(selectedImage);

    }

    private void setInputListeners() {
        binding.skinIssueName.addTextChangedListener(new JournalTextWatcher() {
            @Override
            void onJournalTextChange(String text) {
                viewModel.setSkinIssue(text);
            }
        });

        binding.notesName.addTextChangedListener(new JournalTextWatcher() {
            @Override
            void onJournalTextChange(String text) {
                viewModel.setNotes(text);
            }
        });
    }

    private void setSelectedDate(View button, Callable function) {

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
            function.callSelectedDate(datePicker.getHeaderText());
            ((MaterialButton) button).setText(datePicker.getHeaderText());
        });
    }

    private void setSelectedTime(View button) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTitleText("Select Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();

        timePicker.show(getSupportFragmentManager(), "TimePicker");
        timePicker.addOnPositiveButtonClickListener(selection -> {
            String formattedTime = timePicker.getHour() + ":" + timePicker.getMinute();

            String formatted12Hrs = formatTo12Hrs(formattedTime);

            viewModel.setSelectedTime(formatted12Hrs);

            ((MaterialButton) button).setText(formatTo12Hrs(formattedTime));
        });
    }

    private interface Callable {
        void callSelectedDate(String selectedDate);
    }

    @SuppressLint("NewApi")
    private String formatTo12Hrs(String time) {
        try {
            final SimpleDateFormat sdf = simpleDateFormat("H:mm");
            return simpleDateFormat("hh:mm a").format(sdf.parse(time));
        } catch (ParseException e) {
            Log.e(null, e.getMessage());
            return time;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static SimpleDateFormat simpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}