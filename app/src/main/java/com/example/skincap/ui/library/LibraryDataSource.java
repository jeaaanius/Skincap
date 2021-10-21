package com.example.skincap.ui.library;

import java.util.ArrayList;
import java.util.List;

public class LibraryDataSource {
     public static final List<Library> skinIssueList = new ArrayList<Library>() {
        {
            add(new Library("Acne Papule", "", "", ""));
            add(new Library("Sunspots", "", "", ""));
            add(new Library("Whiteheads", "", "", ""));
            add(new Library("Blackheads", "", "", ""));
            add(new Library("Fungal Acne", "", "", ""));
            add(new Library("Folliculitis", "", "", ""));
            add(new Library("Perioral Dermatitis","","",""));
            add(new Library("Milia","","",""));
        }
    };

}
