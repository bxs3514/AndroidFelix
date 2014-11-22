package afelix.service.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

enum MODE{
	ADD, DELETE, MODIFY
};

public class AFelixSQLiteHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "AFelixSQLiteHelper";
	
	private static final String DATABASE_NAME = "AndroidFelix.db";
	private static final int DATABASE_INITIAL_VERSION = 1;
	
	private ArrayList<String> TABLE_NAME = null;
	private HashMap<String, String> TABLE_COLUMN = null;
	private static final String TABLE_COLUMN_ID = "_id";
	
	private String DATABASE_UPGRADE = "";
	
	public AFelixSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_INITIAL_VERSION);
		TABLE_NAME = new ArrayList<String>();
		TABLE_COLUMN = new HashMap<String, String>();
		
		String singleTableName = null;
		for(Iterator<String> i = TABLE_NAME.iterator(); i.hasNext();){
			singleTableName = i.next();
			DATABASE_UPGRADE += "create table" 
					+ singleTableName + "(" + TABLE_COLUMN_ID
					+ " integer primary key autoincrement, " + TABLE_COLUMN.get(singleTableName)
					+ " text not null" + "\n";
		}
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_UPGRADE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(TAG, "Upgrading database from version" + oldVersion + " to " + newVersion);
		
		String singleTableName = null;
		DATABASE_UPGRADE = "";
		for(Iterator<String> i = TABLE_NAME.iterator(); i.hasNext();){
			singleTableName = i.next();
			DATABASE_UPGRADE += "DROP TABLE IF EXISTS" + singleTableName + "\n";
		}
		for(Iterator<String> i = TABLE_NAME.iterator(); i.hasNext();){
			singleTableName = i.next();
			DATABASE_UPGRADE += "create table" 
					+ singleTableName + "(" + TABLE_COLUMN_ID
					+ " integer primary key autoincrement, " + TABLE_COLUMN.get(singleTableName)
					+ " text not null" + "\n";
		}
		onCreate(db);
	}

	
	
	public ArrayList<String> getTABLE_NAME() {
		return TABLE_NAME;
	}

	public HashMap<String, String> getTABLE_COLUMN() {
		return TABLE_COLUMN;
	}

	public void setTable(String table, String column, MODE mode){
		DATABASE_UPGRADE = "";
		switch(mode){
		case ADD:
			TABLE_NAME.add(table);
			TABLE_COLUMN.put(table, column);
			DATABASE_UPGRADE += "create table" 
					+ table + "(" + TABLE_COLUMN_ID
					+ " integer primary key autoincrement, " + column
					+ " text not null" + "\n";
			
			//db.execSQL(DATABASE_UPGRADE);
			break;
		case DELETE:
			if(!TABLE_NAME.isEmpty()){
				TABLE_NAME.remove(table);
				TABLE_COLUMN.remove(table);
				DATABASE_UPGRADE = "DROP TABLE IF EXISTS" + table + "\n";
				
				//db.execSQL(DATABASE_UPGRADE);
			}else{
				Log.e(TAG, "Not have any table now.");
			}
			break;
		case MODIFY:
			if(TABLE_COLUMN.containsKey(table)){
				TABLE_COLUMN.put(table, column);
			}else{
				Log.e(TAG, "Not have any table now.");
			}
			break;
		default:
			break;
		}
	}
	
	
}
