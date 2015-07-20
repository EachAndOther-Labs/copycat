package com.eachandother.copycat.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Path path;
    private Paint paint;
    private float lastX, lastY;
    private final RectF dirtyRect = new RectF();
    private static final float STROKE_WIDTH = 15f, HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private final String TAG = this.getClass().getName();
    private CopycatHelper copycat = new CopycatHelper();

    public DrawingView(Context c, AttributeSet attrs) {
        super(c, attrs);

        // we set a new Path
        path = new Path();

        // and we set a new Paint with the desired attributes
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the path with the paint on the canvas when onDraw
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        copycat.setViewDimensions(w, h);
    }

    // when ACTION_DOWN start touch according to the lastX,lastY values
    private void startTouch(float x, float y) {
        Point pt = new Point((int)x,(int)y);
        copycat.penDown(pt);
        path.moveTo(x, y);
        this.lastX = x;
        this.lastY = y;
    }

    // when ACTION_MOVE move touch according to the x, y values
    private void moveTouch(float x, float y, MotionEvent e) {
        // Start tracking the dirty region.
        resetDirtyRect(x, y);

        // When the hardware tracks events faster than
        // they can be delivered to the app, the
        // event will contain a history of those skipped points.
        int historySize = e.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            float historicalX = e.getHistoricalX(i);
            float historicalY = e.getHistoricalY(i);
            expandDirtyRect(historicalX, historicalY);
            path.lineTo(historicalX, historicalY);
        }

        // After replaying history, connect the line to the touch point.
        path.lineTo(x, y);
        //Log.d(TAG, p.lastX + ", " + p.lastY);
        //copycat.drawPoint(p);
    }

    public void clearCanvas() {
        path.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        copycat.penUp();
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            startTouch(x, y);
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            moveTouch(x, y, event);
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            moveTouch(x, y, event);
            upTouch();
        }

        // Include half the stroke width to avoid clipping.
        invalidate(
                (int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
        return true;
    }

    /**
     * Called when replaying history to ensure the dirty region
     * includes all points.
     */
    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private void resetDirtyRect(float x, float y) {

        // The lastX and lastY were set when the ACTION_DOWN
        // motion event occurred.
        dirtyRect.left = Math.min(lastX, x);
        dirtyRect.right = Math.max(lastX, x);
        dirtyRect.top = Math.min(lastY, y);
        dirtyRect.bottom = Math.max(lastY, y);
    }
}


