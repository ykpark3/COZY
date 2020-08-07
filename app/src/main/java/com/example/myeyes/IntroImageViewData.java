package com.example.myeyes;

import android.media.Image;
import android.widget.ImageView;

public class IntroImageViewData {

    private String introText;
    private int image;

    public IntroImageViewData(String introText, int image){
        this.introText = introText;
        this.image = image;
    }

    public String getIntroText() {
        return this.introText;
    }

    public int getImage() {
        return image;
    }
}
