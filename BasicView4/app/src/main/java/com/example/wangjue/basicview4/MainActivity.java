package com.example.wangjue.basicview4;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    TimePicker timePicker;
    DatePicker datePicker;
    int hour, minute;

    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        datePicker = findViewById(R.id.datePicker);

        showTimeDialog();
    }

    private void showTimeDialog() {
        new TimePickerDialog(MainActivity.this, (timePicker, i, il) -> {
            hour = i;
            minute = il;

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            Date date = new Date();
            String strDate = timeFormat.format(date);

            Toast.makeText(getBaseContext(), "You have selected " + strDate, Toast.LENGTH_SHORT).show();
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
        {
        }
    }

    public void onClick(View view) {
//      Toast.makeText(getBaseContext(), "Time selected: " + timePicker.getHour() + ":" + timePicker.getMinute(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), "Date selected: " + datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth()
                + "\n" + "Time selected: " + timePicker.getHour() + ":" + timePicker.getMinute(), Toast.LENGTH_SHORT).show();
    }
}
