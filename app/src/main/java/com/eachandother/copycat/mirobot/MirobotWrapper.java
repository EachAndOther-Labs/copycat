package com.eachandother.copycat.mirobot;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by richeyryan on 13/07/2015.
 */
public class MirobotWrapper {
    private final String TAG = this.getClass().getName();
    private String url;
    private Boolean busy = false;
    private String currentMessageId;
    private String currentMessage;
    String versionId;
    private String stopId = "42stopid42";
    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();

    public enum MIROBOT_DIRECTION {
        forward, backward
    }

    public enum MIROBOT_ROTATE_DIRECTION {
        left, right
    }

    public WebSocketClient socket;

    public MirobotWrapper(String url) {
        this.url = url;
        try {
            URI address = new URI(url);
            socket = new WebSocketClient(address) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d(TAG, "Successfully connected to Mirobot");
                    Log.d(TAG, handshakedata.toString());
                    versionId = MirobotJSONHelper.generateId();
                    String versionJSON = MirobotJSONHelper.versionNumJSON(versionId);
                    //sendJson(versionJSON, versionId);
                    socket.send(versionJSON);
                }

                @Override
                public void onMessage(String message) {
                    Log.d(TAG, message);
                    JSONObject response;
                    String id, status, msg;

                    try {
                        response = new JSONObject(message);
                        Log.d(TAG, response.toString());
                        try {
                            id = response.getString("id");
                            status = response.getString("status");
//                            if(status.equals("accepted") && !id.equals(currentMessageId)){
//                                sendStop();
//                            }
                            if (status.equals("complete")) {
                                if(id.equals(versionId)){
                                    Log.d(TAG, "Version: " + response.getString("msg"));
                                }
                                if(id.equals(currentMessage)) {
                                    //busy = false;
                                    Log.d(TAG, "==== Mirobot request finished ====");
                                }
                            }
//                            if(status.equals("error")){
//                                msg = response.getString("msg");
//                                if(msg.equals("Previous command not finished")) {
//                                    sendStop();
//                                }
//                            }
//                            if(status.equals("complete") && id.equals(stopId)){
//                                Log.d(TAG, "Stop sent, next: " + currentMessage);
//                                socket.send(currentMessage);
//                            }
                        }
                        catch (JSONException e){
                            Log.d(TAG, "Error parsing JSON", e);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                private void sendStop(){
                    String json = MirobotJSONHelper.stopJSON(stopId);
                    socket.send(json);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d(TAG, "Closed " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.d(TAG, "Web socket error: " + ex.getMessage());
                }
            };
        } catch (URISyntaxException e) {
            Log.d(TAG, "Exception encountered: ", e);
        }
        socket.connect();

        Timer t = new Timer(true);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!busy && queue.size() > 0) {
                    currentMessage = queue.poll();
                    busy = true;
                    Log.d(TAG, "==== Mirobot request sent " + queue.peek() + " size:" + queue.size() + " ====");
                    socket.send(currentMessage);
                }
            }
        },0, 200);
    }

    public void move(MIROBOT_DIRECTION direction, int distance) {
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.moveJSON(direction.name(), distance, id);
        sendJson(jsonString, id);
    }

    public void turn(MIROBOT_ROTATE_DIRECTION direction, int angle) {
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.turnJSON(direction.name(), angle, id);
        sendJson(jsonString, id);
    }

    public void penUp() {
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.penUpJSON(id);

    }

    public void penDown() {
        String id = MirobotJSONHelper.generateId();
        String jsonString = MirobotJSONHelper.penDownJSON(id);
    }

    private void sendJson(String json, String id) {
        currentMessageId = id;
        //Log.d(TAG, "Current message: " + json);
        queue.add(json);
    }
}
