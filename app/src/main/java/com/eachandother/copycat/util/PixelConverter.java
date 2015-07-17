package com.eachandother.copycat.util;

import android.util.Log;

/**
 * Created by richeyryan on 17/07/2015.
 */
public class PixelConverter {
    private float x;
    private float y;
    private Dimension view;
    private Dimension paper;
    private final String TAG = this.getClass().getName();

    public PixelConverter(float x, float y, Dimension view, Dimension paper){
        this.x = x;
        this.y = y;
        this.view = view;
        this.paper = paper;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public float getMMXValue(){
        float xPercentage = this.x / this.view.getWidth();
        return this.paper.getWidth() * xPercentage;
    }

    public float getMMYValue(){
        float yPercentage = this.y / this.view.getHeight();
        return this.paper.getHeight() * yPercentage;
    }
}
