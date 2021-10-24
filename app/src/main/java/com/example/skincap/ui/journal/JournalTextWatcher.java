package com.example.skincap.ui.journal;

import android.text.Editable;
import android.text.TextWatcher;

abstract class JournalTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onJournalTextChange(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    abstract void onJournalTextChange(String text);
}
