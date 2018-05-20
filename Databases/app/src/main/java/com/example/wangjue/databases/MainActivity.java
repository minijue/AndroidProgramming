package com.example.wangjue.databases;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int index = 1;

    String name, email;
    EditText txtName, txtEmail;
    Button btnPrev, btnNext;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);

        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        db = new DBAdapter(this);
        db.open();
        long id = db.insertContact("Wangjue", "jue952@live.cn");
        id = db.insertContact("Zhouli", "sunnyzlwj@hotmail.com");
        db.close();

        db.open();
        /*Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            do {
                DisplayContract(c);
            } while (c.moveToNext());
        }
        */
        displayContact();

        db.close();
    }

    private void displayContact() {
        Cursor c = db.getContact(index);
        if (c.moveToFirst()) {
            name = c.getString(1);
            email = c.getString(2);

            txtName.setText(name);
            txtEmail.setText(email);
        }

        btnPrev.setEnabled(index > 1);
        btnNext.setEnabled(index < db.getContactsCount());
    }

    private void DisplayContract(Cursor c) {
        Toast.makeText(getBaseContext(), "id: " + c.getString(0) + "\n"
                + "Name: " + c.getString(1) + "\n"
                + "Email: " + c.getString(2), Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        db.open();
        switch (view.getId()) {
            case R.id.btnPrev:
                if (index > 1) {
                    --index;
                    displayContact();
                }
                break;
            case R.id.btnNext:
                if (index < db.getContactsCount()) {
                    ++index;
                    displayContact();
                }
                break;
        }
        db.close();
    }
}
