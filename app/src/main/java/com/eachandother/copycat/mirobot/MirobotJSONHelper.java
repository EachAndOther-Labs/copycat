package com.eachandother.copycat.mirobot;

import android.util.Log;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by richeyryan on 11/07/2015.
 */
public final class MirobotJSONHelper {
    private static final String TAG = "MirobotJSONHelper";

    public static String generateId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static String moveJSON(String direction, int distance, String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", direction);
            json.put("arg", distance);
            json.put("id", id);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return json.toString();
    }

    public static String turnJSON(String direction, int angle, String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", direction);
            json.put("arg", angle);
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
}
