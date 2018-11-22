package com.wangjue.blackandwhite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class EmptyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent start = new Intent(this, BaWService.class);
        startService(start);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.finish();
    }

}
