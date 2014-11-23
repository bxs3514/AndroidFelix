/**
 * 
 * @author bxs3514
 *
 * This is a android felix launcher.
 *
 * @lastEdit 11/23/2014
 * 
 */

package afelix.service.controler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AFelixSQLiteHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "AFelixSQLiteHelper";
	
	private static final String DATABASE_NAME = "AndroidFelix.db";
	private static final int DATABASE_INITIAL_VERSION = 1;
	
	//private static final String TABLE_CREATE = "create table "
	
	public AFelixSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_INITIAL_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d(TAG,"Database is about to create.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(TAG, "Upgrading database from version" + oldVersion + " to " + newVersion);
		
	}
}
