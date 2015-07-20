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
    private Dimension A4Size = new Dimension(297, 210);

    public CopycatHelper() {
        mirobot = new MirobotWrapper(MIROBOT_URL);
        state = new CopycatState();
    }

    public void drawPoint(Point pt) {
        orientCopycat(pt);
    }

    public void penUp() {
        this.mirobot.penUp();
    }

    public void penDown(Point pt) {
        orientCopycat(pt);
        this.mirobot.penDown();
    }

    public void setViewDimensions(int w, int h){
        viewSize = new Dimension(w, h);
        Log.d(TAG, "View size is: " + viewSize.getWidth() + " " + viewSize.getHeight());
    }

    private void orientCopycat(Point pt){
        float deltaX = calculateDelta(pt.x, state.getCurrentPosition().x);
        float deltaY = calculateDelta(pt.y, state.getCurrentPosition().y);
        rotate(deltaX, deltaY);
        move(deltaX, deltaY);
    }

    private void move(float deltaX, float deltaY) {
        float distance = calculateDistance(deltaX, deltaY);
        Point pt = new Point(state.getCurrentPosition().x + (int)deltaX, state.getCurrentPosition().y + (int)deltaY);
        state.setCurrentPosition(pt);
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
        PixelConverter converter = new PixelConverter(deltaX, deltaY, viewSize, A4Size);
        double distance = Math.sqrt(Math.pow((double)converter.getMMXValue(), 2) + Math.pow((double)converter.getMMYValue(), 2));
        return (float) distance;
    }
}

