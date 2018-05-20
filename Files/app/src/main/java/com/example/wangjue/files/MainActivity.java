package com.example.wangjue.files;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText textBox;
    static final int READ_BLOCK_SIZE = 100;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textBox = (EditText) findViewById(R.id.editText);
        verifyStoragePermissions(this);
    }

    void onClickSave(View v) {
        String str = textBox.getText().toString();
        try {
            // SD card storage
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();
            File file = new File(directory, "textfile.txt");
            FileOutputStream fout = new FileOutputStream(file);

            // FileOutputStream fout = openFileOutput("textfile.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fout);
            try {
                osw.write(str);
                osw.flush();
                Toast.makeText(getBaseContext(), "File saved successfully", Toast.LENGTH_SHORT).show();
                textBox.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void onClickLoad(View v) {
        try {
            // SD card storage
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            File file = new File(directory, "textfile.txt");
            FileInputStream fin = new FileInputStream(file);

            // FileInputStream fin = openFileInput("textfile.txt");
            InputStreamReader isr = new InputStreamReader(fin);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            StringBuffer s = new StringBuffer("");
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s.append(readString);
                inputBuffer = new char[READ_BLOCK_SIZE];
            }

            textBox.setText(s.toString());
            Toast.makeText(getBaseContext(), "File loaded successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
