package com.example.wangjue.contentproviders;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddTitle(View view) {
        ContentValues values = new ContentValues();
        values.put(BooksProvider.TITLE, ((EditText) findViewById(R.id.txtTitle)).getText().toString());
        values.put(BooksProvider.ISBN, ((EditText) findViewById(R.id.txtISBN)).getText().toString());

        Uri uri = getContentResolver().insert(BooksProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onClickRetrieveTitles(View view) {
        Uri allTitles = Uri.parse("content://com.example.wangjue.provider.Books/books");
        Cursor c;
        CursorLoader cursorLoader = new CursorLoader(this, allTitles, null, null, null, "title desc");
        c = cursorLoader.loadInBackground();
        if (c != null && c.moveToNext()) {
            do {
                Toast.makeText(this, c.getString(c.getColumnIndex(BooksProvider._ID)) +
                        ", " + c.getString(c.getColumnIndex(BooksProvider.TITLE)) +
                        ", " + c.getString(c.getColumnIndex(BooksProvider.ISBN)), Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
