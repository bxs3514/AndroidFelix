/**
 * 
 * @author bxs3514
 *
 * This is a android felix launcher.
 *
 * @lastEdit 11/18/2014
 * 
 */

package afelix.service.controler.database;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import afelix.afelixservice.androidfelix.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


enum MODE{
	ADD, DROP, DELETE, INSERT
};

public class BundleDataCenter extends Activity implements DatabaseControler, OnClickListener{
	private static final String TAG = "DataControler";
	
	private static final String TABLE_COLUMN_ID = "_id";
	private String DATABASE_UPGRADE = "";
	private String DATABASE_QUERY = "";
	private File[] bundleFiles = null;
	private ArrayList<String> as = null;
	private HashMap<String, String> hs = null;
	
	private ListView allBundlesList = null;
	private TextView info;
	private AFelixSQLiteHelper afHelper = null;
	private Cursor cursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_bundle_list);
	    afHelper = new AFelixSQLiteHelper(getApplicationContext());
	    info = (TextView)findViewById(R.id.textView2);
	    initDatabase();
	    allBundlesList = (ListView)findViewById(R.id.all_bundle_list);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		afHelper.close();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			default:
				break;
		}
	}
	
	@Override
	public void addTable(String table, ArrayList<String> column,
			HashMap<String, String> type) {
		// TODO Auto-generated method stub
		setTable(table, column, type, MODE.ADD);
	}

	@Override
	public void dropTable(String table) {
		// TODO Auto-generated method stub
		setTable(table, null, null, MODE.DROP);
	}

	@Override
	public void Insert(String table, ArrayList<String> columnElements) {
		// TODO Auto-generated method stub
		setTable(table, columnElements, null, MODE.INSERT);
	}

	@Override
	public void Delete(String table, ArrayList<String> factor) {
		// TODO Auto-generated method stub
		setTable(table, factor, null, MODE.DELETE);
	}
	
	private void initDatabase(){
		
		ArrayList<String> as = new ArrayList<String>();
		HashMap<String, String> hs = new HashMap<String, String>();
		
		try{
			as.add("1");
			as.add("EnglishDictionary.jar");
			as.add(Environment.getExternalStorageDirectory().getPath() + "/Afelixdata/Bundle/");
			setTable("BundleLocationTable", as, null, MODE.INSERT);
			as.clear();
		}catch(SQLiteException se){
			Log.e(TAG, "The table isn't existing, about to create a new one.", se);
			as.add("BundleId");
			as.add("BundleName");
			as.add("BundleLocation");
			
			hs.put("BundleId", "INTEGER");
			hs.put("BundleName", "VARCHAR(255)");
			hs.put("BundleLocation", "VARCHAR(255)");
			
			setTable("BundleLocationTable", as, hs, MODE.ADD);
			as.clear();
			hs.clear();
			
			as.add("1");
			as.add("EnglishDictionary.jar");
			as.add(Environment.getExternalStorageDirectory().getPath() + "/Afelixdata/Bundle/");
			setTable("BundleLocationTable", as, null, MODE.INSERT);
			as.clear();
		}
	}
	
	
	public ArrayAdapter<String> Query(String select, String table, String where){
		if(select != null){
			if(where != null)
				DATABASE_QUERY = "select " + select + " from " + table + " where " + where;
			else
				DATABASE_QUERY = "select " + select + " from " + table;
				
		}else{
			if(where != null)
				DATABASE_QUERY = "select * from " + table + " where " + where;
			else
				DATABASE_QUERY = "select * from " + table;
		}
		
		Cursor cursor = afHelper.getReadableDatabase().rawQuery(DATABASE_QUERY, null);
		
		if(!cursor.equals(null)){
			//info.setText(cursor.getColumnCount());
			//info.setText("");
			//info.append(cursor.getString(0));
			ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this, 
					android.R.layout.simple_list_item_1,
					new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)}
					);
			allBundlesList.setAdapter(mArrayAdapter);/**/
			//return mArrayAdapter;
		}else Toast.makeText(this, "Can't find the data!", Toast.LENGTH_LONG).show();
		return null;
	}
	
	public void QueryTest(){
		Cursor cursor = afHelper.getReadableDatabase()
				.rawQuery("select BundleLocation from BundleLocationTable", null);
		if (cursor.moveToFirst() == false){
			Toast.makeText(this, "Empty!", Toast.LENGTH_LONG).show();
		}else{
			
			Toast.makeText(this, cursor.getString(0), Toast.LENGTH_LONG).show();
		}
		//info.setText(cursor.getString(0));
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
			info.setText(afHelper.getReadableDatabase().toString());
			
			//Toast.makeText(this, afHelper.getReadableDatabase().toString(), Toast.LENGTH_LONG).show();
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE);
			}catch(SQLiteException se){
				Log.e(TAG, table + " has existed.", se);
				Toast.makeText(this, table + " has existed.", Toast.LENGTH_LONG).show();
			}
			break;
		case DROP:
				DATABASE_UPGRADE = "DROP TABLE IF EXISTS" + table;
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE);
				//db.execSQL(DATABASE_UPGRADE);
			break;
		case INSERT:
			DATABASE_UPGRADE = "insert into " + table + " values(null, ?, ?, ?)";
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE,
						column.toArray(new String[column.size()]));
				QueryTest();
			}catch(SQLiteException se){
				Log.e(TAG, "Can't find the table to insert.", se);
				//Toast.makeText(this, "Can't insert!!!", Toast.LENGTH_LONG).show();
			}
			//Query(null, table, null);
			break;
		case DELETE:
			break;
		default:
			break;
			
		}
	}
}
