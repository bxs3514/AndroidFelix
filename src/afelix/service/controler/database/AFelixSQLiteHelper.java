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
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AFelixSQLiteHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "AFelixSQLiteHelper";
	
	private static final String DATABASE_NAME = "AndroidFelix.db";
	private static final int DATABASE_INITIAL_VERSION = 1;
	
	//private ArrayList<String> TABLE_NAME = null;
	//private HashMap<String, String> TABLE_COLUMN = null;
	//private static final String TABLE_COLUMN_ID = "_id";
	
	private String DATABASE_CREATE = "";
	
	public AFelixSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_INITIAL_VERSION);
		//this.DATABASE_CREATE = DATABASE_CREATE;
		/*TABLE_NAME = new ArrayList<String>();
		TABLE_COLUMN = new HashMap<String, String>();
		
		String singleTableName = null;
		for(Iterator<String> i = TABLE_NAME.iterator(); i.hasNext();){
			singleTableName = i.next();
			DATABASE_UPGRADE += "create table" 
					+ singleTableName + "(" + TABLE_COLUMN_ID
					+ " integer primary key autoincrement, " + TABLE_COLUMN.get(singleTableName)
					+ " text not null" + "\n";
		}*/
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d(TAG,"Database is about to create.");
		//try{
			//db.execSQL(DATABASE_CREATE);
		//}catch(SQLiteException se){
			//Log.e(TAG, "Database create fail: " + se.toString(), se);
		//}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(TAG, "Upgrading database from version" + oldVersion + " to " + newVersion);
		
		/*String singleTableName = null;
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
		onCreate(db);*/
	}

	
	
	/*public ArrayList<String> getTABLE_NAME() {
		return TABLE_NAME;
	}

	public HashMap<String, String> getTABLE_COLUMN() {
		return TABLE_COLUMN;
	}*/

	
}
