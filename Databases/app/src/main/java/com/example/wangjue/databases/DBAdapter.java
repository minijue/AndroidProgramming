package com.example.wangjue.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "CREATE TABLE contacts (_id integer primary key autoincrement, name text not null, email text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertContact(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_EMAIL},
                null, null, null, null, null);
    }

    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_EMAIL},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateContact(long rowId, String name, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public int getContactsCount() {
        Cursor c = db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME, KEY_EMAIL},
                null, null, null, null, null);
        return c.getCount();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
}
