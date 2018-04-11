package com.example.wangjue.dialogfragmentexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment1 dialogFragment = Fragment1.newInstance("Are you sure you want to do this?");
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        Toast.makeText(this, "User clicks on OK", Toast.LENGTH_SHORT).show();
    }

    public void doNegativeClick() {
        Toast.makeText(this, "User clicks on Cancel", Toast.LENGTH_SHORT).show();
    }
}
