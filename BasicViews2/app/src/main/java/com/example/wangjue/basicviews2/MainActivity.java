package com.example.wangjue.basicviews2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    static int progress;
    ProgressBar progressBar;

    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = 0;
        progressBar = findViewById(R.id.progressbar);
        progressBar.setMax(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus = doSomeWork();
                    progressBar.setProgress(progressStatus);
                }

//                handler.post(() -> {
//                    progressBar.setVisibility(View.GONE);
//                });
            }

            private int doSomeWork() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ++progress;
            }
        }).start();
    }
}
