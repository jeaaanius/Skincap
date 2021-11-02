package com.example.skincap.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideBinder {

    public static <T> void bindImage(final ImageView imageView, T data) {
        Glide.with(imageView)
                .asBitmap()
                .override(imageView.getWidth(), imageView.getHeight())
                .load(data)
                .into(imageView);
    }
}
