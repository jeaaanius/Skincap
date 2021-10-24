package com.example.skincap.ui.journal;

import android.app.Application;

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

    public CreateJournalViewModel(Application application) {
        super(application);

        journalDao = AppDatabase.getInstance()
                .journalDao();
    }

    void addJournal() {
        final Journal journal = new Journal(
                skinIssue,
                startDate,
                expectedDueDate,
                selectedTime,
                notes
        );

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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }
}
