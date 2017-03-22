package com.example.wangjue.quickcontactbadge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.QuickContactBadge;

public class MainActivity extends AppCompatActivity {
    QuickContactBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        badge = (QuickContactBadge) findViewById(R.id.badge);
        badge.assignContactFromPhone("15070988966", false);
    }
}
