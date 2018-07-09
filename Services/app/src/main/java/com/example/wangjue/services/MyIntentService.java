package com.example.wangjue.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class MyIntentService extends IntentService {
    private Thread thread = new Thread();

    public MyIntentService() {
        super("MyIntentServiceName");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        thread.start();

        try {
            int result = DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            Log.d("Intent Service", "Downloaded " + result + " bytes.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private int DownloadFile(URL url) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }
}
