package com.example.gs.handledemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static java.lang.Thread.sleep;

public class Activity2 extends AppCompatActivity implements View.OnClickListener{
    public static final int UPDATE = 1;

    private Handler handler1;//将mHandler指定轮询的Looper
    private Handler handler2;//将mHandler指定轮询的Looper
    private LoopThread loopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        method1();
        method2();
        method3();

        Button button = (Button) findViewById(R.id.method1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.method2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.method3);
        button.setOnClickListener(this);
    }

    //方法1
    public void method1(){
        MyThread thread = new MyThread();
        thread.start();//千万别忘记开启这个线程

        delay(1000);  //在Handler初始化的时候，thread.looper还没有初始化，所以加一个延时

        handler1 = new Handler(thread.looper){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE:
                        // 在这里可以进行UI操作
                        Log.d("当前子线程是--1--->",Thread.currentThread()+"");
                        break;
                    default:
                        break;
                }
            };
        };
    }

    //方法2
    public void method2(){
        //实例化一个特殊的线程HandlerThread，必须给其指定一个名字
        HandlerThread thread = new HandlerThread("handler thread");
        thread.start();//千万不要忘记开启这个线程
        //将mHandler与thread相关联
        handler2 = new Handler(thread.getLooper()){
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case UPDATE:
                        // 在这里可以进行UI操作
                        Log.d("当前子线程是--2--->",Thread.currentThread()+"");
                        break;
                    default:
                        break;
                }
            };
        };
    }

    //方法3
    public void method3(){
        loopThread = new LoopThread();
        Thread thread = new Thread(loopThread);
        thread.start();
    }

    public class LoopThread implements Runnable {

        public Handler mHandler = null;

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case UPDATE:
                            // 在这里可以进行UI操作
                            Log.d("当前子线程是--3--->",Thread.currentThread()+"");
                            break;
                        default:
                            break;
                    }
                }
            };
            Looper.loop();
        }
    }


    private void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //子线程
    class MyThread extends Thread{
        private Looper looper;//取出该子线程的Looper
        public void run() {
            Looper.prepare();//创建该子线程的Looper
            looper = Looper.myLooper();//取出该子线程的Looper
            Looper.loop();//只要调用了该方法才能不断循环取出消息
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.method1:
                //下面是主线程发送消息
                Message message1 = new Message();
                message1.what = UPDATE;
                handler1.sendMessage(message1);
                break;
            case R.id.method2:
                //下面是主线程发送消息
                Message message2 = new Message();
                message2.what = UPDATE;
                handler2.sendMessage(message2);
                break;
            case R.id.method3:
                //下面是主线程发送消息
                Message message3 = new Message();
                message3.what = UPDATE;
                loopThread.mHandler.sendMessage(message3);
                break;
            default:
                break;
        }
    }

}
