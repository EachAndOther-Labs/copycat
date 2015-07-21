package com.eachandother.copycat.util;

import android.graphics.Point;

/**
 * Created by richeyryan on 16/07/2015.
 */
public class CopycatState {

    private int previousX;
    private int currentX;
    private int previousY;
    private int currentY;
    private float previousAngle;
    private float angle;
    public final static int STARTING_POINT_X = -15, STARTING_POINT_Y = -15;
    boolean penDown = false;

    public CopycatState(){
        currentX = STARTING_POINT_X;
        currentY = STARTING_POINT_Y;
        previousX = STARTING_POINT_X;
        previousY = STARTING_POINT_Y;
        previousAngle = 0;
        angle = 0;
    }

    public int getPreviousX() {
        return previousX;
    }

    public void setPreviousX(int previousX) {
        this.previousX = previousX;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.previousX = currentX;
        this.currentX = currentX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void setPreviousY(int previousY) {
        this.previousY = previousY;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY){
        this.previousY = this.currentY;
        this.currentY = currentY;
    }

    public float getPreviousAngle() {
        return previousAngle;
    }

    public void setPreviousAngle(float previousAngle) {
        this.previousAngle = previousAngle;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.previousAngle = this.angle;
        this.angle = angle;
    }

    public void resetState(){
        currentX = STARTING_POINT_X;
        currentY = STARTING_POINT_Y;
        previousAngle = 0;
        angle = 0;
    }
}
