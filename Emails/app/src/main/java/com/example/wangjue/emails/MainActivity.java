package com.example.wangjue.emails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        String[] to = {"jue952@126.com", "jue952@yeah.net"};
        String[] cc = {"993352@qq.com"};
        sendEmail(to, cc, "Hello", "Hello my friends!");
    }

    private void sendEmail(String[] emailAddresses, String[] carbonCopies, String subjects, String messages) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAddresses;
        String[] cc = carbonCopies;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjects);
        emailIntent.putExtra(Intent.EXTRA_TEXT, messages);

        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Email"));
    }
}
