package com.example.skincap.ui.home;

public class ChildModel {
    private final String skin_image;
    private final String skinName;

    public ChildModel(String skin_image, String skinName){
        this.skin_image = skin_image;
        this.skinName = skinName;
    }
    public String getSkinImage() {
        return skin_image;
    }
    public String getSkinName() {
        return skinName;
    }
}