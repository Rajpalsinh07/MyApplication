package login1.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import static android.R.attr.name;

/**
 * Created by Moksha on 12/18/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DatabaseVERSION = 1;
    public static final String DatabaseName = "contact.db";


    public static final String table_name = "contact";


    public static final String Column_Id = "id";
    public static final String Column_name = "name";
    public static final String Column_phnno = "phnno";
    public static final String Column_emailid = "emailid";
    public static final String Column_password = "password";
    SQLiteDatabase db;

    public static final String Table_Create = "create table contact(id integer primary key," + " name text, phnno integer,emailid text, password text)";

    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Table_Create);
        this.db = db;
    }

    public void insertContact(String name, String phon, String email, String pass) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Column_name, name);
        values.put(Column_phnno, phon);
        values.put(Column_emailid, email);
        values.put(Column_password, pass);

        db.insert(table_name, null, values);
        db.close();
    }

        public int searchpass(String UserName, String password) {
        db = this.getReadableDatabase();
        String query = "select * from " + table_name + " where name='" + UserName + "' AND password = '" + password + "'";
        Log.e(">>>>", query);
        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }

    public int countUser() {
        db = this.getReadableDatabase();
        String query = "select * from " + table_name;
        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "Drop Table If Exists" + table_name;
        db.execSQL("query");
        this.onCreate(db);

    }
}