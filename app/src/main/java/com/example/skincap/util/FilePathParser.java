package com.example.skincap.util;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.File;

public class FilePathParser {

    public static Uri parseImagePath(@Nullable String absolutePath) {
        if (absolutePath == null) {
            return null;
        }
        return Uri.fromFile(new File(absolutePath));
    }
}
