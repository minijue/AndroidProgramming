package com.example.wangjue.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    IntentFilter intentFilter;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(), "File downloaded!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(intentReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");

        registerReceiver(intentReceiver, intentFilter);
    }

    public void startService(View view) {
        // startService(new Intent(getBaseContext(), MyService.class));
        Intent intentSerivce = new Intent(getBaseContext(), MyIntentService.class);
        intentSerivce.putExtra("myurl", "http://www.amazon.com/somefile.pdf");
        startService(intentSerivce);
    }

    public void stopService(View view) {
        // stopService(new Intent(getBaseContext(), MyService.class));
        stopService(new Intent(getBaseContext(), MyIntentService.class));
    }
}
