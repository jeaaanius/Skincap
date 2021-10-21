package com.example.skincap.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
public class Journal {

    private String title;
    private String startDate;
    private String expectedDate;
    private String selectedTime;

    @PrimaryKey
    @NotNull
    private String journalId;

    public Journal(String title, String startDate, String expectedDate, String selectedTime) {
        this.title = title;
        this.startDate = startDate;
        this.expectedDate = expectedDate;
        this.selectedTime = selectedTime;
        this.journalId = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }
}
