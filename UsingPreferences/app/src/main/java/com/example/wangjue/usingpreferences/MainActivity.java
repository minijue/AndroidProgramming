package com.example.wangjue.usingpreferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLoad(View view) {
        Intent i = new Intent("com.example.wangjue.usingpreferences.AppPreferenceActivity");
        startActivity(i);
    }

    public void onClickDisplay(View view) {

    }

    public void onClickModify(View view) {
    }
}
