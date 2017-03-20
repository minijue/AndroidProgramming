package com.example.wangjue.chromometertest;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    Chronometer ch;
    Button start;
    Boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ch = (Chronometer) findViewById(R.id.test);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener((source) -> {
            ch.setBase(SystemClock.elapsedRealtime());
            if (!started) {
                ch.start();
                start.setText("停止");
            } else {
                ch.stop();
                start.setText("启动");
            }
            started = !started;
        });

    }
}
