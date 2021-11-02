package com.example.skincap.ui.journal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.skincap.R;
import com.example.skincap.databinding.ActivityCreateJournalBinding;
import com.example.skincap.model.Journal;
import com.example.skincap.util.FilePathParser;
import com.example.skincap.util.GlideBinder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CreateJournalActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 0x1435;

    private ActivityCreateJournalBinding binding;

    private CreateJournalViewModel viewModel;

    private String journalId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CreateJournalViewModel.class);

        binding = ActivityCreateJournalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setExtras();

        setInputListeners();
        setListeners();
    }

    private void setExtras() {
        try {
            Journal journal = getIntent()
                    .getParcelableExtra("journal_parcelable");

            if (journal == null) {
                return;
            }

            journalId = journal.getJournalId();

            binding.skinIssueName.setText(journal.getTitle());
            binding.startDateButton.setText(journal.getStartDate());
            binding.expectDateButton.setText(journal.getExpectedDate());
            binding.timeNotifButton.setText(journal.getSelectedTime());
            binding.notes.setText(journal.getNote());

            final Uri imageUri = FilePathParser
                    .parseImagePath(journal.getImagePath());

            if (imageUri != null) {
                getSelectedImage(imageUri);
            }

        } catch (NullPointerException e) {
            Log.e("setExtras", e.getMessage());
        }
    }

    private void setListeners() {
        binding.expectDateButton.setOnClickListener(button ->
                setSelectedDate(button, selectedDate -> viewModel.setExpectedDueDate(selectedDate)));

        binding.startDateButton.setOnClickListener(button ->
                setSelectedDate(button, selectedDate -> viewModel.setStartDate(selectedDate)));

        binding.includePhotoButton.setOnClickListener((v -> setGalleryPicker()));

        binding.timeNotifButton.setOnClickListener(this::setSelectedTime);

        binding.cancel.setOnClickListener(e -> finish());

        binding.save.setOnClickListener(e -> {
            viewModel.addJournal(journalId);
            finish();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            try {
                assert data != null;

                getSelectedImage(Matisse.obtainResult(data).get(0));
                viewModel.setImagePath(Matisse.obtainPathResult(data).get(0));
            } catch (Exception e) {
                Toast.makeText(this, "Error loading image preview.", Toast.LENGTH_SHORT).show();
                Log.e(null, e.getMessage());
            }
        }
    }

    private void getSelectedImage(@Nullable final Uri data) {
        binding.includePhotoView.setVisibility(View.VISIBLE);
        GlideBinder.bindImage(binding.includePhotoView, data);
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

    private void setGalleryPicker() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(1)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(true) // Default is `true`
                .maxOriginalSize(10)
                .originalEnable(true)
                .theme(R.style.SkinCapStyle)
                .autoHideToolbarOnSingleTap(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private static class GifSizeFilter extends Filter {

        private final int maxWidth;
        private final int minHeight;
        private final int maxSize;

        public GifSizeFilter(int maxWidth, int minHeight, int maxSize) {
            this.maxWidth = maxWidth;
            this.minHeight = minHeight;
            this.maxSize = maxSize;
        }

        @Override
        protected Set<MimeType> constraintTypes() {
            return new HashSet<MimeType>() {
                {
                    add(MimeType.GIF);
                }
            };
        }

        @Override
        public IncapableCause filter(Context context, Item item) {
            if (!needFiltering(context, item)) {
                return null;
            }
            final Point point = PhotoMetadataUtils.getBitmapBound(
                    context.getContentResolver(), item.getContentUri()
            );

            if (point.x < minHeight || point.y < minHeight || item.size > maxSize) {
                return new IncapableCause(IncapableCause.DIALOG,
                        context.getString(
                                R.string.error_gif,
                                maxWidth,
                                String.valueOf(PhotoMetadataUtils.getSizeInMB(maxSize))
                        ));
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}