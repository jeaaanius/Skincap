package com.example.skincap.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    public MainActivityViewModel() {

    }

    private final MutableLiveData<Integer> _destinationId = new MutableLiveData<>(null);
    private final LiveData<Integer> destinationId = _destinationId;

    public void setDestinationId(int destinationId) {
        _destinationId.setValue(destinationId);
    }

    public LiveData<Integer> getDestinationId() {
        return destinationId;
    }
}