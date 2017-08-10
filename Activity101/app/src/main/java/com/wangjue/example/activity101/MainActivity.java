package com.wangjue.example.activity101;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStart() {
        super.onStart();

        progressBar = findViewById(R.id.progress);
        TextView tv = findViewById(R.id.tv);

        //       progressDialog = ProgressDialog.show(this, "Please wait", "Progressing...", true);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
//                progressDialog.dismiss();
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                tv.setVisibility(TextView.INVISIBLE);
            }
        }.start();
    }
}
