package com.ardian.ecrlink.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ardian.ecrlink.MainActivity;
import com.ardian.ecrlink.wifi.httpd.NanoHTTPD;

import java.io.IOException;

/**
 * Created by Ardian Iqbal Yusmartito on 26/09/22
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
class DebugWebSocket extends WebSocket {
    private Context context;
    private final boolean DEBUG;
    public int value;

    //method info handshake
    public DebugWebSocket(NanoHTTPD.IHTTPSession handshake, boolean debug, Context context) {
        super(handshake);
        Log.d("DebugWebSocket", "DebugWebSocket: " + handshake);
        DEBUG = debug;
        this.context = context;
        ((Activity)this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Device Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPong(WebSocketFrame pongFrame) {
        if (DEBUG) {
            System.out.println("P " + pongFrame);
            Log.d("onPong", "onPong: " + pongFrame);
        }
    }

    //method log all data (yang terakhir terpanggil ketika callback)
    @Override
    protected void onMessage(WebSocketFrame messageFrame) {
        try {
            messageFrame.setUnmasked();
            sendFrame(messageFrame);
            Log.d("TAG  : TEST", "onMessage: TEST " + messageFrame);
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(context, "Log Data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onClose(WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
        if (DEBUG) {
            System.out.println("C [" + (initiatedByRemote ? "Remote" : "Self") + "] " + (code != null ? code : "UnknownCloseCode[" + code + "]") + (reason != null && !reason.isEmpty() ? ": " + reason : ""));
            Log.d("onClose", "onClose: " + code + " " + reason);
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Device Disconnected", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    protected void onException(IOException e) {
        e.printStackTrace();
    }

    //method tembak data balik (terpanggil ke 2)
    @Override
    public synchronized void sendFrame(WebSocketFrame frame) throws IOException {
        if (DEBUG) {
            System.out.println("S " + frame);
            Log.d("sendFrame", "sendFrame: " + frame);
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(context, "Mengirim data kembali", Toast.LENGTH_SHORT).show();
                }
            });
        }
        super.sendFrame(frame);
    }


    //method handle data masuk (terpanggil pertama)
    @Override
    protected void handleWebsocketFrame(WebSocketFrame frame) throws IOException {
        if (DEBUG) {
            System.out.println("R " + frame);
            Log.d("HandleWebSocketFrame", "handleWebsocketFrame: " + frame);
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ((MainActivity)context).setTextView(frame.getTextPayload());
                    //((CoreActivity)context).setTextView(frame.getTextPayload());
                    Toast.makeText(context, "Data Masuk" , Toast.LENGTH_SHORT).show();
                }
            });
        }
        super.handleWebsocketFrame(frame);
    }

}
