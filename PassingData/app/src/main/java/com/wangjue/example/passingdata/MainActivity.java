package com.wangjue.example.passingdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        // Intent i = new Intent(this, SecondActivity.class);
        Intent i = new Intent("com.wangjue.example.passingdata.SecondActivity");
        i.putExtra("str1", "This is a string");
        i.putExtra("age1", 25);

        Bundle extras = new Bundle();
        extras.putString("str2", "This is another string");
        extras.putInt("age2", 35);

        i.putExtras(extras);

        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                StringBuilder sb = new StringBuilder(Integer.toString(data.getIntExtra("age3", 0)));
                sb.append("\n");
                sb.append(data.getData().toString());
                Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
