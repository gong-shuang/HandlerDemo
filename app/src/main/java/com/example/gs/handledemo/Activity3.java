package com.example.gs.handledemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Activity3 extends AppCompatActivity implements View.OnClickListener {
    public static final int SEND = 1;
    public static final String TAG = "SEND";
    private Handler childHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Button button = (Button) findViewById(R.id.send);
        button.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                childHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case SEND:
                                // 在这里可以进行UI操作
                                Log.d(TAG,"这个消息是从-->>" + msg.obj + "过来的，在" + Thread.currentThread()+ "子线程当中执行的");
                                break;
                            default:
                                break;
                        }
                    }

                };
                Looper.loop();//开始轮循
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = childHandler.obtainMessage();
                        msg.what = SEND;
                        msg.obj =  ""+ Thread.currentThread();
                        childHandler.sendMessage(msg);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
