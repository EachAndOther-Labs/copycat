package com.eachandother.copycat.mirobot;

import android.util.Log;
import android.util.Size;

import com.eachandother.copycat.util.RandomString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by richeyryan on 11/07/2015.
 */
public final class MirobotJSONHelper {
    private static final String TAG = "MirobotJSONHelper";
    private static RandomString generator = new RandomString(10);

    public static String generateId() {
        return generator.nextString();
    }

    public static String moveJSON(String direction, int distance, String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("arg", Integer.toString(distance));
            json.put("cmd", direction);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String turnJSON(String direction, int angle, String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", direction);
            json.put("arg", Integer.toString(angle));
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String penUpJSON(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "penup");
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String penDownJSON(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "pendown");
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String beepJSON(int duration, String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "beep");
            json.put("arg", duration);
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String pingJSON(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "ping");
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String versionNumJSON(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "version");
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String stopJSON(String id){
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "stop");
            json.put("id", id);
        }
        catch (JSONException e){
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }
}
