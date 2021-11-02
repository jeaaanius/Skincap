package com.example.skincap.util;

import android.net.Uri;

import java.io.File;

public class FilePathParser {

    public static Uri parseImagePath(String absolutePath) {
        return Uri.fromFile(new File(absolutePath));
    }
}
