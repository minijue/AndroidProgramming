package com.example.wangjue.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    static final int UPDATE_INTERVAL = 1000;
    int counter = 0;
    private Timer timer = new Timer();

    private final IBinder binder = new MyBinder();
    URL[] urls;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();

        doSomethingRepeatedly();

        new DoBackgroundTask().execute(urls);
//            new URL("http://www.google.com/some.pdf"),
//                    new URL("http://www.amazon.com/some.pdf"),
//                    new URL("http://www.baidu.com/some.pdf"),
//                    new URL("http://www.wrox.com/some.pdf"));
        return START_STICKY;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d("My Service", String.valueOf(++counter));
            }
        }, 0, UPDATE_INTERVAL);
    }

    private int DownloadFile(URL url) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
        }
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("Downloading files", String.valueOf(values[0]) + "% downloaded");
            Toast.makeText(getBaseContext(), String.valueOf(values[0]) + "% downloaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Toast.makeText(getBaseContext(), "Downloaded " + aLong + " bytes", Toast.LENGTH_SHORT).show();
            stopSelf();
        }

        @Override
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                publishProgress((int) (((i + 1) / (float) count) * 100));
            }
            return totalBytesDownloaded;
        }
    }
}
