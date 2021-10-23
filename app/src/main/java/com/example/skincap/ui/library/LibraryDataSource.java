package com.example.skincap.ui.library;

import java.util.ArrayList;
import java.util.List;

public class LibraryDataSource {
     public static final List<Library> skinIssueList = new ArrayList<Library>() {
        {
            add(new Library("Acne Papule",
                    "A tiny red lump on the skin. It normally has a diameter of less than 5 millimeters",
                    "",
                    ""));
            add(new Library("Sunspots",
                    "Flat brown spots appear on parts of your skin that have been exposed to the sun.",
                    "",
                    ""));
            add(new Library("Whiteheads",
                    "Little, raised lumps on the skin that are white in color.",
                    "When dead skin cells, oil, and bacteria become stuck within one of your pores.",
                    ""));
            add(new Library("Blackheads",
                    "Small lumps on the skin caused by clogged hair follicles.",
                    "",
                    ""));
            add(new Library("Fungal Acne",
                    "Infection in the hair follicles of your skin.",
                    "",
                    ""));
            add(new Library("Perioral Dermatitis",
                    "A rash involving the skin around the mouth that is inflamed.",
                    "",
                    ""));
            add(new Library("Milia",
                    "Typically develops on the nose and cheeks as a little white lump.",
                    "",
                    ""));
        }
    };

}
