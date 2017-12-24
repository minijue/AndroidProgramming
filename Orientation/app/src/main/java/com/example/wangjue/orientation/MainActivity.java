package com.example.wangjue.orientation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d("StateInfo", "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("StateInfo", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("StateInfo", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("StateInfo", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("StateInfo", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("StateInfo", "onDestroy");
        super.onDestroy();
    }
}
