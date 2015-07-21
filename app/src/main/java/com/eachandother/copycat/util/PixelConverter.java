package com.eachandother.copycat.util;

import android.util.Log;

/**
 * Created by richeyryan on 17/07/2015.
 */
public class PixelConverter {
    private Dimension view;
    private Dimension paper;
    private final String TAG = this.getClass().getName();

    public PixelConverter(Dimension view, Dimension paper){
        this.view = view;
        this.paper = paper;
    }

    public Dimension getView() {
        return view;
    }

    public void setView(Dimension view) {
        this.view = view;
    }

    public Dimension getPaper() {
        return paper;
    }

    public void setPaper(Dimension paper) {
        this.paper = paper;
    }

    public float getMMXValue(float x){
        float xPercentage = x / this.view.getWidth();
        return this.paper.getWidth() * xPercentage;
    }

    public float getMMYValue(float y){
        float yPercentage = y / this.view.getHeight();
        return this.paper.getHeight() * yPercentage;
    }
}
