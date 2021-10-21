package com.example.skincap.ui.home;

public class ChildModel {
    private int skin_image;
    private String skinName;

    public ChildModel(int skin_image, String skinName){
        this.skin_image = skin_image;
        this.skinName = skinName;
    }
    public int getSkinImage() {
        return skin_image;
    }
    public String getSkinName() {
        return skinName;
    }
}