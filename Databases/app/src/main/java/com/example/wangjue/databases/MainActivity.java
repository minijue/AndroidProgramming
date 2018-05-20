package com.example.wangjue.databases;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAdapter db = new DBAdapter(this);
        db.open();
        long id = db.insertContact("Wangjue", "jue952@live.cn");
        id = db.insertContact("Zhouli", "sunnyzlwj@hotmail.com");
        db.close();

        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            do {
                DisplayContract(c);
            } while (c.moveToNext());
        }
        db.close();
    }

    private void DisplayContract(Cursor c) {
        Toast.makeText(getBaseContext(), "id: " + c.getString(0) + "\n"
                + "Name: " + c.getString(1) + "\n"
                + "Email: " + c.getString(2), Toast.LENGTH_SHORT).show();
    }
}
