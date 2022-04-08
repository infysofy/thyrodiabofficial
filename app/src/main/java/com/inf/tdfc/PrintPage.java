package com.inf.tdfc;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class PrintPage extends AppCompatActivity {

    TextView myLabel;
    ProgressDialog pDialog;
    // will enable user to enter any text to be printed
    EditText myTextbox;

    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String message="";
    Button openButton,sendButton,closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_page);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("msg");
        // we are going to have three buttons for specific functions
        openButton = (Button) findViewById(R.id.open);
        sendButton = (Button) findViewById(R.id.send);
        closeButton = (Button) findViewById(R.id.close);
        // text label and input box
        myLabel = (TextView) findViewById(R.id.label);
        myTextbox = (EditText) findViewById(R.id.entry);
        myTextbox.setText(message);
       // openButton.setVisibility(View.GONE);
        //closeButton.setVisibility(View.GONE);
        //sendButton.setVisibility(View.GONE);
        //findBT();
        // open bluetooth connection
        try {
            findBT();
            openBT();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        openButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    findBT();
                    openBT();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // close bluetooth connection
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    closeBT();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // send data typed by the user to be printed
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                   sendData();

                   //onBackPressed();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    // close the connection to bluetooth printer.
    private void displayLoader() {
        pDialog = new ProgressDialog(PrintPage.this);
        pDialog.setMessage(" Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    void sendData() throws IOException {
        try {

            // the text typed by the user
            String msg =myTextbox.getText().toString();
            msg += "\n";

            mmOutputStream.write(msg.getBytes());

            // tell the user data were sent
            myLabel.setText("Data sent.");
            closeBT();
            finish();
            //closeBT();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // this will find a bluetooth printer device
    void findBT() {
        displayLoader();
        try {

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
                //findBT();
                //openBT();
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                  //  if (device.getName().equals("TD_PRINT")) {
                    mmDevice = device;
                    break;
                   // }
                }
            }
            myLabel.setText("Bluetooth device found.");
            //sendButton.setVisibility(View.VISIBLE);
            openButton.setVisibility(View.VISIBLE);
            pDialog.dismiss();
        }catch(Exception e){
            pDialog.dismiss();
            e.printStackTrace();

        }
    }
    // close the connection to bluetooth printer.
    // tries to open a connection to the bluetooth printer device
    void openBT() throws IOException {
        displayLoader();
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Bluetooth Opened");
            pDialog.dismiss();
           // sendButton.setVisibility(View.VISIBLE);
            //openButton.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            pDialog.dismiss();
        }
    }
    /*
     * after opening a connection to bluetooth printer device,
     * we have to listen and check if a data were sent to be printed.
     */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void closeBT() throws IOException {
        displayLoader();
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
            pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            pDialog.dismiss();
        }
    }

}
