package com.vnu.parabolicflight.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SensorDataManager";

	// Contacts table name
	private static final String TABLE_ACCELEROMETER = "AccelerometerData";
	private static final String TABLE_GYROSCOPE = "GryroscopeData";
	private static final String TABLE_GRAVITY = "GravityData";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TIME = "time";
	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";
	private static final String KEY_Z = "z";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ACCELEROMETER_TABLE = "CREATE TABLE "
				+ TABLE_ACCELEROMETER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_TIME + " TEXT," + KEY_X + " TEXT," + KEY_Y + " TEXT,"
				+ KEY_Z + " TEXT" + ")";

		String CREATE_GYROSCOPE_TABLE = "CREATE TABLE " + TABLE_GYROSCOPE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
				+ KEY_X + " TEXT," + KEY_Y + " TEXT," + KEY_Z + " TEXT" + ")";

		String CREATE_GRAVITY_TABLE = "CREATE TABLE " + TABLE_GRAVITY + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
				+ KEY_X + " TEXT," + KEY_Y + " TEXT," + KEY_Z + " TEXT" + ")";

		db.execSQL(CREATE_ACCELEROMETER_TABLE);
		db.execSQL(CREATE_GYROSCOPE_TABLE);
		db.execSQL(CREATE_GRAVITY_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELEROMETER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GYROSCOPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAVITY);

		// Create tables again
		onCreate(db);
	}

	public void addAccelerometerData(Data data) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIME, String.valueOf(data.getTime()));
		values.put(KEY_X, Float.toString(data.getX()));
		values.put(KEY_Y, Float.toString(data.getY()));
		values.put(KEY_Z, Float.toString(data.getZ()));

		// Inserting Row
		db.insert(TABLE_ACCELEROMETER, null, values);
		db.close(); // Closing database connection
	}

	public void addGyroscopeData(Data data) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIME, String.valueOf(data.getTime()));
		values.put(KEY_X, Float.toString(data.getX()));
		values.put(KEY_Y, Float.toString(data.getY()));
		values.put(KEY_Z, Float.toString(data.getZ()));

		// Inserting Row
		db.insert(TABLE_GYROSCOPE, null, values);
		db.close(); // Closing database connection
	}

	public void addGravityData(Data data) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIME, String.valueOf(data.getTime()));
		values.put(KEY_X, Float.toString(data.getX()));
		values.put(KEY_Y, Float.toString(data.getY()));
		values.put(KEY_Z, Float.toString(data.getZ()));

		// Inserting Row
		db.insert(TABLE_GRAVITY, null, values);
		db.close(); // Closing database connection
	}

	public void exportToFile(File file, String type) {
		String selectQuery = "SELECT  * FROM ";

		if (type == "gyroscope") {
			selectQuery = selectQuery + TABLE_GYROSCOPE;
		} else if (type == "gravity") {
			selectQuery = selectQuery + TABLE_GRAVITY;
		} else {
			selectQuery = selectQuery + TABLE_ACCELEROMETER;
		}

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		try {
			FileWriter fw = new FileWriter(file);

			if (cursor.moveToFirst()) {
				do {
					fw.write(cursor.getString(1) + "\n");
					fw.write(cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + "\n");
				} while (cursor.moveToNext());
			}
			fw.close();

		} catch (IOException e) {
		}
	}
}