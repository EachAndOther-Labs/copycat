package com.eachandother.copycat.util;

import android.graphics.Point;
import android.util.Log;

import com.eachandother.copycat.mirobot.MirobotWrapper;

/**
 * Created by richeyryan on 11/07/2015.
 */
public class CopycatHelper {

//    private final static String MIROBOT_URL = "ws://10.10.100.254:8899/websocket";
    private final static String MIROBOT_URL = "ws://192.168.0.3:8899/websocket";

    private final String TAG = this.getClass().getName();
    private MirobotWrapper mirobot;
    private CopycatState state;
    private Dimension viewSize;
    private Dimension A4Size;
    private PixelConverter converter;

    public CopycatHelper() {
        mirobot = new MirobotWrapper(MIROBOT_URL);
        state = new CopycatState();
        A4Size = new Dimension(297, 210);
    }

    public void drawPoint(float x, float y) {
        orientCopycat(x, y);
    }

    public void penUp() {
        this.mirobot.penUp();
    }

    public void penDown(float x, float y) {
        orientCopycat(x, y);
        this.mirobot.penDown();
    }

    public void setViewDimensions(int w, int h) {
        viewSize = new Dimension(w, h);
        converter = new PixelConverter(viewSize, A4Size);
//        Log.d(TAG, "View size is: " + viewSize.getWidth() + " " + viewSize.getHeight());
    }

    private void orientCopycat(float x, float y) {
//        Log.d(TAG, "==== BEGIN MOVE x:" + Float.toString(x) + " y:" + Float.toString(y) + " ====");
        //Log.d(TAG, Float.toString(x) + " " + Float.toString(y));
        float deltaX = calculateDelta(x, state.getCurrentX());
        float deltaY = calculateDelta(y, state.getCurrentY());
//        Log.d(TAG, Float.toString(deltaX) + " " + Float.toString(deltaY));
        rotate(deltaX, deltaY);
        move(deltaX, deltaY);
//        Log.d(TAG, "==== END MOVE ====");
    }

    private void move(float deltaX, float deltaY) {
        int newX, newY;
        int distance = calculateDistance(deltaX, deltaY);
//        if(distance > 0) {
        newX = state.getCurrentX() + (int) deltaX;
        newY = state.getCurrentY() + (int) deltaY;
//        Log.d(TAG, "Old and new x: " + Integer.toString(state.getCurrentX()) + " " + Integer.toString(newX));
//        Log.d(TAG, "Old and new y: " + Integer.toString(state.getCurrentY()) + " " + Integer.toString(newY));
        state.setCurrentX(newX);
        state.setCurrentY(newY);
        mirobot.move(MirobotWrapper.MIROBOT_DIRECTION.forward, distance);
//        }
    }

    private void rotate(float deltaX, float deltaY) {
        float angle = calculateRotation(deltaX, deltaY);
        MirobotWrapper.MIROBOT_ROTATE_DIRECTION direction = calculateRotationDirection(angle);
        if (direction == MirobotWrapper.MIROBOT_ROTATE_DIRECTION.left) {
            angle = Math.abs(angle);
        }
//        Log.d(TAG, "Mirobot turn: " + direction.toString() + " by " + Float.toString(angle) + " degrees");
        this.mirobot.turn(direction, Math.round(angle));
        state.setAngle(angle);
    }

    private float calculateDelta(float a, float b) {
//        Log.d(TAG, "Delta: " + Float.toString(a) + " " + Float.toString(b));
        return a - b;
    }

    private float calculateRotation(float deltaX, float deltaY) {
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
//        Log.d(TAG, "Original angle: " + Double.toString(angle));
//        Log.d(TAG, "Previous angle: " + state.getAngle());
        if (angle > 0) {
            angle = state.getPreviousAngle() - angle;
            if (angle > 180) {
                angle -= 360;
            } else if (angle < -180) {
                angle += 360;
            }
        }
//        Log.d(TAG, "Manipulated angle: " + Double.toString(angle));
        return (float) angle;
    }

    private MirobotWrapper.MIROBOT_ROTATE_DIRECTION calculateRotationDirection(float angle) {
        MirobotWrapper.MIROBOT_ROTATE_DIRECTION direction;
        if (angle < 0) {
            direction = MirobotWrapper.MIROBOT_ROTATE_DIRECTION.right;
        } else {
            direction = MirobotWrapper.MIROBOT_ROTATE_DIRECTION.left;
        }
        return direction;
    }

    private int calculateDistance(float deltaX, float deltaY) {
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
//        Log.d(TAG, "PX: " + Double.toString(distance));
        int mmDistance = converter.convertPxToMm(distance);
//        Log.d(TAG, "MM: " + Integer.toString(mmDistance));
        return mmDistance;
    }
}

