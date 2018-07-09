package com.example.wangjue.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    IntentFilter intentFilter;
    MyService serviceBinder;
    Intent i;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = ((MyService.MyBinder) service).getService();
            try {
                URL[] urls = new URL[]{new URL("http://www.google.com/some.pdf"),
                        new URL("http://www.amazon.com/some.pdf"),
                        new URL("http://www.baidu.com/some.pdf"),
                        new URL("http://www.wrox.com/some.pdf")};
                serviceBinder.urls = urls;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            startService(i);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBinder = null;
        }
    };

//    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getBaseContext(), "File downloaded!", Toast.LENGTH_SHORT).show();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        unregisterReceiver(intentReceiver);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("FILE_DOWNLOADED_ACTION");
//
//        registerReceiver(intentReceiver, intentFilter);
//    }

    public void startService(View view) {
        // startService(new Intent(getBaseContext(), MyService.class));
//        Intent intentSerivce = new Intent(getBaseContext(), MyIntentService.class);
//        intentSerivce.putExtra("myurl", "http://www.amazon.com/somefile.pdf");
//        startService(intentSerivce);
        i = new Intent(MainActivity.this, MyService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
//        stopService(new Intent(getBaseContext(), MyIntentService.class));
    }
}
