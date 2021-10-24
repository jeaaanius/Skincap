package com.example.skincap.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.skincap.db.AppDatabase;
import com.example.skincap.model.Journal;

import java.util.List;

public class JournalListViewModel extends ViewModel {

    private LiveData<List<Journal>> journalList;

    public JournalListViewModel() {
        journalList = AppDatabase.getInstance()
                .journalDao()
                .getJournals();
    }

    public LiveData<List<Journal>> getJournals() {
        return journalList;
    }
}
