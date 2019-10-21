package com.example.finalproject.carChargingStation;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

    public class MyDatabaseOpenHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "MyDatabaseFile";
        public static final int VERSION_NUM = 1;
        public static final String TABLE_NAME = "Stations";
        public static final String COL_ID = "_id";
        public static final String COL_TITLE = "TITLE";
        public static final String COL_LATITUDE = "LATITUDE";
        public static final String COL_LONGITUDE = "LONGITUDE";
        public static final String COL_PHONE = "PHONE";


        public MyDatabaseOpenHelper(Activity ctx){
            super(ctx, DATABASE_NAME, null, VERSION_NUM );
        }

        public void onCreate(SQLiteDatabase db)
        {
            //Make sure you put spaces between SQL statements and Java strings:
            db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                    + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_TITLE + " TEXT, " + COL_LATITUDE + " REAL, "
                    + COL_LONGITUDE + " REAL, " + COL_PHONE + " TEXT)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

            //Delete the old table:
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create a new table:
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

            //Delete the old table:
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create a new table:
            onCreate(db);
        }
    }

