package com.eachandother.copycat.util;

import android.graphics.Point;

/**
 * Created by richeyryan on 16/07/2015.
 */
public class CopycatState {

    private Point previousPosition;
    private Point currentPosition;
    private float previousAngle;
    private float angle;
    public final static Point STARTING_POINT = new Point(-15,-15);
    boolean penDown = false;

    public CopycatState(){
        currentPosition = STARTING_POINT;
        previousPosition = STARTING_POINT;
        previousAngle = 0;
        angle = 0;
    }

    public Point getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Point previousPosition) {
        this.previousPosition = previousPosition;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        previousPosition = this.currentPosition;
        this.currentPosition = currentPosition;
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
        currentPosition = STARTING_POINT;
        previousAngle = 0;
        angle = 0;
    }
}
