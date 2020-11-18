package com.example.promar_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    EditText set_ip;
    String host = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        set_ip = findViewById(R.id.ip);
    }
    public void send(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                try {

                    int post = 30000;
//                    String host = "192.168.0.16";
                    socket = new Socket(host, post);

                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    String str = editText.getText().toString().trim();
                    sendTextMsg(out, str);
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void set(View view) {
        host =  set_ip.getText().toString().trim();
    }
    public void sendTextMsg(DataOutputStream out, String msg) throws IOException {
        byte[] bytes = msg.getBytes();
        long len = bytes.length;
        //先发送长度，在发送内容
        out.writeLong(len);
        out.write(bytes);
    }
}