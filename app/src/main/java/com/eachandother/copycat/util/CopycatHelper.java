package com.eachandother.copycat.util;

import android.graphics.Point;
import android.util.Log;
import com.eachandother.copycat.mirobot.MirobotWrapper;

/**
 * Created by richeyryan on 11/07/2015.
 */
public class CopycatHelper {

    private final static String MIROBOT_URL = "ws://10.10.100.254:8899/websocket";
    private final String TAG = this.getClass().getName();
    private MirobotWrapper mirobot;
    private CopycatState state;
    private Dimension viewSize;
    private Dimension A4Size;
    private PixelConverter converter;

    public CopycatHelper() {
        mirobot = new MirobotWrapper(MIROBOT_URL);
        state = new CopycatState();
        A4Size = new Dimension(297,210);
    }

    public void drawPoint(float x, float y) {
        orientCopycat(x,y);
    }

    public void penUp() {
        this.mirobot.penUp();
    }

    public void penDown(float x, float y) {
        orientCopycat(x, y);
        this.mirobot.penDown();
    }

    public void setViewDimensions(int w, int h){
        viewSize = new Dimension(w, h);
        converter = new PixelConverter(viewSize, A4Size);
        Log.d(TAG, "View size is: " + viewSize.getWidth() + " " + viewSize.getHeight());
    }

    private void orientCopycat(float x, float y){
        float deltaX = calculateDelta(x, state.getCurrentX());
        float deltaY = calculateDelta(y, state.getCurrentY());
        rotate(deltaX, deltaY);
        move(deltaX, deltaY);
    }

    private void move(float deltaX, float deltaY) {
        float distance = calculateDistance(deltaX, deltaY);
        state.setCurrentX(state.getPreviousX() + (int)deltaX);
        state.setCurrentY(state.getPreviousY() + (int)deltaY);
        this.mirobot.move(MirobotWrapper.MIROBOT_DIRECTION.forward, (int)distance);
    }

    private void rotate(float deltaX, float deltaY){
        float angle = calculateRotation(deltaX, deltaY);
        MirobotWrapper.MIROBOT_ROTATE_DIRECTION direction = calculateRotationDirection(angle);
        this.mirobot.turn(direction, (int)angle);
        state.setAngle(angle);
    }

    private float calculateDelta(float a, float b) {
        return a - b;
    }

    private float calculateRotation(float deltaX, float deltaY) {
        double angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        if (angle > 0) {
            angle = state.getPreviousAngle() - angle;
            if (angle > 180) {
                angle -= 360;
            } else if (angle < -180) {
                angle += 360;
            }
        }
        return (float) angle;
    }

    private MirobotWrapper.MIROBOT_ROTATE_DIRECTION calculateRotationDirection(float angle){
        MirobotWrapper.MIROBOT_ROTATE_DIRECTION direction;
        if (angle < 0) {
            direction = MirobotWrapper.MIROBOT_ROTATE_DIRECTION.right;
        } else {
            direction = MirobotWrapper.MIROBOT_ROTATE_DIRECTION.left;
        }
        return direction;
    }

    private float calculateDistance(float deltaX, float deltaY) {
        double distance = Math.sqrt(Math.pow((double)converter.getMMXValue(deltaX), 2) + Math.pow((double)converter.getMMYValue(deltaY), 2));
        return (float) distance;
    }
}

