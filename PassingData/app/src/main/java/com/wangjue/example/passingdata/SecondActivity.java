package com.wangjue.example.passingdata;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        StringBuilder sb = new StringBuilder(getIntent().getStringExtra("str1"));
        sb.append("\n");
        sb.append(Integer.toString(getIntent().getIntExtra("age1", 0)));
        sb.append("\n");

        Bundle bundle = getIntent().getExtras();

        sb.append(bundle.getString("str2"));
        sb.append("\n");
        sb.append(Integer.toString(bundle.getInt("age2")));
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        Intent i = new Intent();
        i.putExtra("age3", 45);
        i.setData(Uri.parse("Something passed back to main activity"));

        setResult(RESULT_OK, i);

        finish();
    }
}
