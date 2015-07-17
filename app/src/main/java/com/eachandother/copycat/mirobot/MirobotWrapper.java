package com.eachandother.copycat.mirobot;

import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by richeyryan on 13/07/2015.
 */
public class MirobotWrapper {
    private final String TAG = this.getClass().getName();
    private String url;
    public enum MIROBOT_DIRECTION {
        forward, backward
    }
    public enum MIROBOT_ROTATE_DIRECTION{
        left, right
    }

    public MirobotWrapper(String url) {
        this.url = url;

    }

    public void move(MIROBOT_DIRECTION direction, int distance) {
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.moveJSON(direction.name(), distance, id);
        Log.d(TAG, jsonString);
    }

    public void turn(MIROBOT_ROTATE_DIRECTION direction, int angle) {
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.turnJSON(direction.name(), angle, id);
        Log.d(TAG, jsonString);
    }

    public void penUp(){
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.penUpJSON(id);
        Log.d(TAG, jsonString);
    }

    public void penDown(){
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.penDownJSON(id);
        Log.d(TAG, jsonString);
    }

    private class MiroBotMsg extends AsyncTask<ArrayList<Point>, Void, Void> {
        private final String TAG = this.getClass().getName();

        @Override
        protected Void doInBackground(ArrayList<Point>... params) {
            Log.d("id", MirobotJSONHelper.generateId());
            Log.d(TAG, "async");
            return null;
        }
    }
}
