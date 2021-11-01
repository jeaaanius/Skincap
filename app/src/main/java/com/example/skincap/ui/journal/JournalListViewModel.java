package com.example.skincap.ui.journal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.skincap.db.AppDatabase;
import com.example.skincap.model.Journal;

import java.util.List;

public class JournalListViewModel extends ViewModel {

    private final LiveData<List<Journal>> journalList;

    public JournalListViewModel() {
        journalList = AppDatabase.getInstance()
                .journalDao()
                .getJournals();
    }

    public LiveData<List<Journal>> getJournals() {
        return journalList;
    }

    public void deleteJournal(String id) {
        AppDatabase.deleteJournal(id);
    }
}
