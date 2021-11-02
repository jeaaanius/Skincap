package com.example.skincap.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
public class Journal implements Parcelable {

    private String title;
    private String startDate;
    private String expectedDate;
    private String selectedTime;
    private String note;
    private String imagePath;

    @PrimaryKey
    @NotNull
    private String journalId;

    public Journal(
            String title,
            String startDate,
            String expectedDate,
            String selectedTime,
            String note,
            String imagePath,
            @Nullable String journalId
    ) {
        this.title = title;
        this.startDate = startDate;
        this.expectedDate = expectedDate;
        this.selectedTime = selectedTime;
        this.note = note;
        this.imagePath = imagePath;
        this.journalId = journalId == null ? UUID.randomUUID().toString() : journalId;
    }

    protected Journal(Parcel in) {
        journalId = in.readString();
        title = in.readString();
        startDate = in.readString();
        expectedDate = in.readString();
        selectedTime = in.readString();
        note = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<Journal> CREATOR = new Creator<Journal>() {
        @Override
        public Journal createFromParcel(Parcel in) {
            return new Journal(in);
        }

        @Override
        public Journal[] newArray(int size) {
            return new Journal[size];
        }
    };

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    @NonNull
    public String getJournalId() {
        return journalId;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(journalId);
        dest.writeString(title);
        dest.writeString(startDate);
        dest.writeString(expectedDate);
        dest.writeString(selectedTime);
        dest.writeString(note);
        dest.writeString(imagePath);
    }

    public static class Builder {

        private String title;
        private String startDate;
        private String expectedDate;
        private String selectedTime;
        private String note;
        private String imagePath;
        private String id;

        public Builder setId(@Nullable String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setExpectedDate(String expectedDate) {
            this.expectedDate = expectedDate;
            return this;
        }

        public Builder setSelectedTime(String selectedTime) {
            this.selectedTime = selectedTime;
            return this;
        }

        public Builder setNote(String note) {
            this.note = note;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Journal build() {
            return new Journal(
                    title, startDate, expectedDate,
                    selectedTime, note, imagePath, id
            );
        }
    }
}
