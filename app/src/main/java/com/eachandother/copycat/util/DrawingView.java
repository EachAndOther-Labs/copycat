package com.eachandother.copycat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Canvas canvas;
    private Path path;
    Context context;
    private Paint paint;
    private float x, y;
    private static final float TOLERANCE = 5;
    private final String TAG = this.getClass().getName();
    private CopycatHelper copycat = new CopycatHelper();

    public DrawingView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        path = new Path();

        // and we set a new Paint with the desired attributes
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(15f);
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
        Bitmap mBitmap;
        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
        copycat.setViewDimensions(w, h);
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        Point pt = new Point((int)x,(int)y);
        copycat.penDown(pt);
        path.moveTo(x, y);
        this.x = x;
        this.y = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - this.x);
        float dy = Math.abs(y - this.y);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(this.x, this.y, (x + this.x) / 2, (y + this.y) / 2);
            this.x = x;
            this.y = y;
        }
        Point p = new Point(Math.round(x), Math.round(y));
        Log.d(TAG, p.x + ", " + p.y);
        copycat.drawPoint(p);
    }

    public void clearCanvas() {
        path.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        copycat.penUp();
        path.lineTo(x, y);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}


