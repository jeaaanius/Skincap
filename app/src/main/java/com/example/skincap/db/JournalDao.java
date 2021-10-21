package com.example.skincap.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.skincap.model.Journal;

import java.util.List;

@Dao
public interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetJournal(Journal journal);

    @Query("SELECT * FROM journal")
    LiveData<List<Journal>> getJournals();
}