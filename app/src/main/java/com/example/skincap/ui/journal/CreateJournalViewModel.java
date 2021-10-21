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

    public CreateJournalViewModel(Application application) {
        super(application);

        journalDao = AppDatabase.getInstance()
                .journalDao();
    }

    void addJournal(Journal journal) {
        executorService.execute(() -> journalDao.insetJournal(journal));
    }
}
