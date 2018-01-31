package com.example.wangjue.basicview4;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {
    TimePicker timePicker;

    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        showTimeDialog();
    }

    private void showTimeDialog() {
        new TimePickerDialog(MainActivity.this, (timePicker, i, il) -> {
            Toast.makeText(getBaseContext(), "Time selected: " + timePicker.getHour() + ":" + timePicker.getMinute(),
                    Toast.LENGTH_SHORT).show();
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
        {
        }
    }

    public void onClick(View view) {
        Toast.makeText(getBaseContext(), "Time selected: " + timePicker.getHour() + ":" + timePicker.getMinute(),
                Toast.LENGTH_SHORT).show();
    }
}
