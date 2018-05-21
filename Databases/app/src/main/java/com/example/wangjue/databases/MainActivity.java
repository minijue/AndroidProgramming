package com.example.wangjue.databases;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private static int index = 1;
    TreeSet<Integer> idset = new TreeSet<Integer>();

    String name = "", email = "";
    EditText txtName, txtEmail;
    Button btnPrev, btnNext, btnUpdate, btnDel;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);

        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDel = findViewById(R.id.btnDel);

        db = new DBAdapter(this);
        db.open();
        long id = db.insertContact("Wangjue", "jue952@live.cn");
        idset.add((int) id);
        id = db.insertContact("Zhouli", "sunnyzlwj@hotmail.com");
        idset.add((int) id);
        id = db.insertContact("Wang Zhizhou", "doudou@126.com");
        idset.add((int) id);
        db.close();

        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDel.setEnabled(false);

        db.open();
        /*Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            do {
                DisplayContract(c);
            } while (c.moveToNext());
        }
        */
        displayContact(index);

        db.close();
    }

    private void displayContact(/*Cursor c*/int i) {
//        Toast.makeText(getBaseContext(), "id: " + c.getString(0) + "\n"
//                + "Name: " + c.getString(1) + "\n"
//                + "Email: " + c.getString(2), Toast.LENGTH_SHORT).show();
        Cursor c = db.getContact(i);
        if (c.moveToFirst()) {
            btnUpdate.setEnabled(true);

            name = c.getString(1);
            email = c.getString(2);

            btnPrev.setEnabled(idset.lower(i) != null);
            btnNext.setEnabled(idset.higher(i) != null);
            btnUpdate.setEnabled(true);
            btnDel.setEnabled(true);
        }
        if (i == 0) {
            name = "";
            email = "";
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDel.setEnabled(false);
        }

        txtName.setText(name);
        txtEmail.setText(email);
    }

    public void onClick(View view) {
        db.open();
        switch (view.getId()) {
            case R.id.btnPrev:
                if (idset.contains(index)) {
                    Integer I = idset.lower(index);
                    if (I != null) {
                        index = I.intValue();
                        displayContact(index);
                    }
                }
                break;
            case R.id.btnNext:
                if (idset.contains(index)) {
                    Integer I = idset.higher(index);
                    if (I != null) {
                        index = I.intValue();
                        displayContact(index);
                    }
                }
                break;
            case R.id.btnUpdate:
                String newName = txtName.getText().toString();
                String newEmail = txtEmail.getText().toString();
                if (idset.contains(index) &&
                        (!name.equals(newName) || !email.equals(newEmail))) {
                    if (db.updateContact(index, newName, newEmail)) {
                        Toast.makeText(this, "Update Successful.", Toast.LENGTH_SHORT).show();
                        displayContact(index);
                    } else {
                        Toast.makeText(this, "Update failed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "数据没有改变，不需要更新！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDel:
                if (idset.contains(index)) {
                    if (db.deleteContact(index)) {
                        idset.remove(index);
                        Integer I = idset.higher(index);
                        if (I != null) {
                            index = I.intValue();
                        } else {
                            if (idset.size() > 0)
                                index = idset.last().intValue();
                            else
                                index = 0;
                        }
                        displayContact(index);
                        Toast.makeText(this, "Delete successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Delete failed.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        db.close();
    }
}
