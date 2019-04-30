package com.shoniz.saledistributemobility.infrastructure.wialon;

import android.util.Log;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;


public class WialonClient {


    private String serverMessage;
    private String serverIp = "2.186.229.209"; //your computer IP address
    private int serverPort = 20332;
    //    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    private Socket socket;
    //   PrintWriter out;
    private OutputStream out;
    private BufferedReader in;

    private CommonPackage commonPackage;
    private IWialonListener wiolonListener;


    public WialonClient(CommonPackage commonPackage, String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.commonPackage = commonPackage;
    }

    public static byte[] bytedPacket(String msg) {
        Charset characterSet = Charset.forName("US-ASCII");
        return msg.getBytes(characterSet);
    }

    //    public void setMessageListener(OnMessageReceived mMessageListener) {
//        this.mMessageListener = mMessageListener;
//    }

    public void setWiolonListener(IWialonListener wiolonListener) {
        this.wiolonListener = wiolonListener;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(String message) throws WialonSendMessageError {
        if (out != null) {
            try {
                out.write(bytedPacket(message));
                out.flush();
            } catch (IOException e) {
                WialonSendMessageError error = new WialonSendMessageError(commonPackage);
                throw error;
            }
        }
    }

//    public void sendMessage(byte[] message) {
//        if (out != null) {
//            try {
//                out = socket.getOutputStream();
//                out.write(message);
//                out.flush();
//            } catch (IOException e) {
//
//                handleError(new UncaughtException(commonPackage, e));
//            }
//        }
//    }

    protected void onProgressUpdate(String resp) {
        // in the arrayList we add the messaged received from server

        if (resp.startsWith("#AL#")) {
            // login response
            resp = resp.replace("#AL#", "");
            wiolonListener.onLogin(resp.equals("1"));

        } else if (resp.startsWith("#AD#")) {
            Log.e("Shoniz", resp);
            resp = resp.replace("#AD#", "");
            if (resp.equals("1")) {
                // success_to_send_point
            } else {
                //error_to_send_point
            }
        }
    }

    public void stopClient() {
        mRun = false;
    }


    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
//    public interface OnMessageReceived {
//        public void messageReceived(String message);
//    }

    public void run() {

        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(serverIp);

            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            socket = new Socket(serverAddr, serverPort);

            try {

                //send the message to the server
                out = socket.getOutputStream();

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                wiolonListener.onRun();
                while (mRun) {
                    serverMessage = in.readLine();

                    if (serverMessage != null)// && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
//                        mMessageListener.messageReceived(serverMessage);
                        onProgressUpdate(serverMessage);
                    //}
                    serverMessage = null;

                }


                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");

            } catch (Exception e) {
                wiolonListener.onSocketClosed(commonPackage.getContext());
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }
}