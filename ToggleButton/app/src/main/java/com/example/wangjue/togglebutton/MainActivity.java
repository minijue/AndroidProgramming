package com.example.wangjue.togglebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton toggle;
    Switch switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = (ToggleButton) findViewById(R.id.toggle);
        switcher = (Switch) findViewById(R.id.switcher);

        final LinearLayout test = (LinearLayout) findViewById(R.id.test);
        CompoundButton.OnCheckedChangeListener listener = (button, isChecked) -> {
            if (isChecked) {
                test.setOrientation(LinearLayout.VERTICAL);
                toggle.setChecked(true);
                switcher.setChecked(true);
            } else {
                test.setOrientation(LinearLayout.HORIZONTAL);
                toggle.setChecked(false);
                switcher.setChecked(false);
            }
        };

        toggle.setOnCheckedChangeListener(listener);
        switcher.setOnCheckedChangeListener(listener);
    }
}
