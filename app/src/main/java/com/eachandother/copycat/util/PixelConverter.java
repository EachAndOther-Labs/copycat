package com.eachandother.copycat.util;

import android.util.Log;

/**
 * Created by richeyryan on 17/07/2015.
 */
public class PixelConverter {
    private Dimension view;
    private Dimension paper;
    private float percentage;
    private final String TAG = this.getClass().getName();

    public PixelConverter(Dimension view, Dimension paper){
        this.view = view;
        this.paper = paper;
        float widthPercent = paper.getWidth() / view.getWidth();
        float heightPercent = paper.getHeight() / view.getHeight();
        percentage = widthPercent + heightPercent / 2;
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

    public int convertPxToMm(double px){
        return (int)Math.round(px * percentage);
    }
}
