package com.example.gs.handledemo;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeText = (Button) findViewById(R.id.button1);
        changeText.setOnClickListener(this);
        changeText = (Button) findViewById(R.id.button2);
        changeText.setOnClickListener(this);
        changeText = (Button) findViewById(R.id.button3);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                intent = new Intent(this, Activity1.class);
                startActivity(intent);
                break;
            case R.id.button2:
                intent = new Intent(this, Activity2.class);
                startActivity(intent);
                break;
            case R.id.button3:
                intent = new Intent(this, Activity3.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
