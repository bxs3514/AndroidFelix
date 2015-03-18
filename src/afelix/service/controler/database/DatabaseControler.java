/**
 * 
 * @author bxs3514
 *
 * This is database controler.
 *
 * @lastEdit 12/6/2014
 * 
 */

package afelix.service.controler.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

enum MODE{
	ADD, DROP, DELETE, INSERT, REPLACE
};


public class DatabaseControler implements IDatabaseControler{

private static final String TAG = "DataControler";
	
	protected AFelixSQLiteHelper afHelper;
	private Cursor cursor;
	
	private static final String TABLE_COLUMN_ID = "id";
	private String DATABASE_UPGRADE = "";
	private String DATABASE_QUERY = "";
	private ArrayList<HashMap<String,String>> queryRes;
	private HashMap<String,String> queryRawRes;
	
	private String[] columnName;
	//private Bundle installAndroidBundle;
	
	public DatabaseControler(Context context){
		afHelper = new AFelixSQLiteHelper(context);
		queryRes = new ArrayList<HashMap<String,String>>();
	}
	
	@Override
	public void addTable(String table, ArrayList<String> column,
			HashMap<String, String> type) {
		
		setTable(table, column, type, MODE.ADD);
	}

	@Override
	public void dropTable(String table) {
		
		setTable(table, null, null, MODE.DROP);
	}

	@Override
	public void Insert(String table, ArrayList<String> columnElements) {
		
		setTable(table, columnElements, null, MODE.INSERT);
	}

	@Override
	public void Delete(String table, ArrayList<String> factor) {
		
		setTable(table, factor, null, MODE.DELETE);
	}

	@Override
	public void Replace(String table, ArrayList<String> columnElements) {
		
		setTable(table, columnElements, null, MODE.REPLACE);
	}
	
	private void setTable(String table, ArrayList<String> column, 
			HashMap<String, String> type, MODE mode) throws SQLiteException{
		
		DATABASE_UPGRADE = "";
		switch(mode){
		case ADD:
			//TABLE_NAME.add(table);
			//TABLE_COLUMN.put(table, column);
			String singleColumn;
			int no = 0;
			DATABASE_UPGRADE = "create table " 
					+ table + " (" + TABLE_COLUMN_ID
					+ " integer primary key autoincrement, ";
			for(Iterator<String> i = column.iterator(); i.hasNext();){
				singleColumn = i.next();
				
				if(no == column.size() - 1)
					DATABASE_UPGRADE += singleColumn + " " + type.get(singleColumn) + ")";
				else
					DATABASE_UPGRADE += singleColumn + " " + type.get(singleColumn) + ", ";
				
				no++;
			}
			//Toast.makeText(this, afHelper.getReadableDatabase().toString(), Toast.LENGTH_LONG).show();
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE);
			}catch(SQLiteException se){
				Log.e(TAG, table + " has existed.", se);
				//Toast.makeText(this, table + " has existed.", Toast.LENGTH_LONG).show();
			}
			break;
		case DROP:
			DATABASE_UPGRADE = "DROP TABLE IF EXISTS " + table;
			afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE);
			break;
		case INSERT:
			//Query(null, table, null);
			int n = afHelper.getReadableDatabase().rawQuery("select * from " + table, null).getColumnCount();
			DATABASE_UPGRADE = "insert into " + table + " values(null,";
			for(int i = 0; i < n - 1; i++){
				if(i != n - 2)
					DATABASE_UPGRADE += "?, ";
				else DATABASE_UPGRADE += "?)";
			}
			
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE,
						column.toArray(new String[column.size()]));
				
			}catch(SQLiteException se){
				//Log.e(TAG, "Can't insert for " + se.toString(), se);
				//Toast.makeText(this, "Can't find the table to insert.", Toast.LENGTH_LONG).show();
			}
			break;
		case DELETE:
			DATABASE_UPGRADE = "delete TABLE IF EXISTS " + table;
			afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE);
			break;
		case REPLACE:
			n = afHelper.getReadableDatabase().rawQuery("select * from " + table, null).getColumnCount();
			DATABASE_UPGRADE = "insert or replace into " + table + " values(null,";
			for(int i = 0; i < n - 1; i++){
				if(i != n - 2)
					DATABASE_UPGRADE += "?, ";
				else DATABASE_UPGRADE += "?)";
			}
			
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE,
						column.toArray(new String[column.size()]));
				
			}catch(SQLiteException se){
				Log.e(TAG, "Can't insert for " + se.toString(), se);
				//Toast.makeText(this, "Can't find the table to insert.", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public ArrayList<HashMap<String,String>> Query(String SqlQuery){
		cursor = afHelper.getReadableDatabase().rawQuery(SqlQuery, null);
		columnName = cursor.getColumnNames();
		analyseCursor(cursor);
		
		return queryRes;
	}
	
	@Override
	public ArrayList<HashMap<String,String>> Query(String[] select, 
			String table, String where){
		
		if(select != null){
			String wholeSelect = "";
			for(int i = 0; i < select.length; i++){
				
				if(i == select.length - 1)
					wholeSelect += select[i];
				else wholeSelect += select[i] + ",";
			}
			
			if(where != null)
				DATABASE_QUERY = "select " + wholeSelect + " from " + table + " where " + where;
			else
				DATABASE_QUERY = "select " + wholeSelect + " from " + table;
				
		}
		else{
			if(where != null)
				DATABASE_QUERY = "select * from " + table + " where " + where;
			else
				DATABASE_QUERY = "select * from " + table;
		}
		
		cursor = afHelper.getReadableDatabase().rawQuery(DATABASE_QUERY, null);
		columnName = cursor.getColumnNames();
		analyseCursor(cursor);
		
		return queryRes;
	}
	
	public String[] getColumnName() {
		if(cursor == null)
			return null;
		else
			return columnName;
	}
	
	private void analyseCursor(Cursor cursor){
		int n = cursor.getColumnCount();
		
		while(cursor.moveToNext()){
			queryRawRes = new HashMap<String,String>();
			for(int i = 0; i < n; i++){
				queryRawRes.put(columnName[i], cursor.getString(i));
			}
			queryRes.add(queryRawRes);
		}

		cursor.close();
	}
	
	public void clearQuery(){
		queryRes.clear();
	}
	
	public void closeDatabase(){
		this.afHelper.close();
	}
}
