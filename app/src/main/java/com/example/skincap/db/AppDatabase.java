package com.example.skincap.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.skincap.model.Journal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Journal.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract JournalDao journalDao();

    private static AppDatabase INSTANCE = null;

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void setINSTANCE(final Context context) {
        synchronized (AppDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase.class,
                        "SkinCapCache.db"
                )
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
    }

    public static AppDatabase getInstance() {
        return INSTANCE;
    }

    public static void deleteJournal(final String id) {
        executorService.execute(() -> {
            AppDatabase.getInstance()
                    .journalDao()
                    .deleteJournal(id);
        });
    }
}
