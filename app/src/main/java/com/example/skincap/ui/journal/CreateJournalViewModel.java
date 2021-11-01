package com.example.skincap.ui.journal;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.example.skincap.db.AppDatabase;
import com.example.skincap.db.JournalDao;
import com.example.skincap.model.Journal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateJournalViewModel extends AndroidViewModel {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final JournalDao journalDao;

    private String skinIssue;
    private String startDate;
    private String expectedDueDate;
    private String selectedTime;
    private String notes;
    private String imagePath;

    public CreateJournalViewModel(Application application) {
        super(application);

        journalDao = AppDatabase.getInstance()
                .journalDao();
    }

    void addJournal(@Nullable String id) {
        Journal journal = new Journal.Builder()
                .setTitle(skinIssue)
                .setStartDate(startDate)
                .setExpectedDate(expectedDueDate)
                .setSelectedTime(selectedTime)
                .setNote(notes)
                .setImagePath(imagePath)
                .setId(id)
                .build();

        executorService.execute(() -> journalDao.insetJournal(journal));
    }

    public void setExpectedDueDate(String expectedDueDate) {
        this.expectedDueDate = expectedDueDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public void setSkinIssue(String skinIssue) {
        this.skinIssue = skinIssue;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }
}
