package com.example.wangjue.basicviews1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpen = findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener((v ->
                DisplayToast("You have clicked the Open button")
        ));

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener((view -> DisplayToast("You have clicked the Save button")));

        ImageButton imageButton = findViewById(R.id.btnImg1);
        imageButton.setOnClickListener((view -> DisplayToast("You have clicked the Image button")));

        CheckBox checkBox = findViewById(R.id.chkAutoSave);
        checkBox.setOnClickListener((view) -> {
            if (((CheckBox) view).isChecked()) {
                DisplayToast("CheckBox is checked");
            } else {
                DisplayToast("CheckBox is unchecked");
            }
        });

        RadioGroup radioGroup = findViewById(R.id.rdbGp1);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb1 = findViewById(R.id.rdb1);
            if (rb1.isChecked())
                DisplayToast("Option 1 checked");
            else
                DisplayToast("Option 2 checked");
        });

        ToggleButton toggleButton = findViewById(R.id.toggle1);
        toggleButton.setOnClickListener((v) -> {
            if (((ToggleButton) v).isChecked())
                DisplayToast("Toggle button is On");
            else
                DisplayToast("Toggle button is Off");
        });
    }

    private void DisplayToast(String s) {
        Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
    }
}
