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

public class BundleDataCenter extends Activity implements IDatabaseControler, OnClickListener{
	private static final String TAG = "DataControler";
	
	private static final String TABLE_COLUMN_ID = "_id";
	private String DATABASE_UPGRADE = "";
	private String DATABASE_QUERY = "";
	private ArrayList<HashMap<String, String>> allBundles = null;
	private HashMap<String, String> everyBundle = null;
	
	private ArrayAdapter<String> mArrayAdapter = null;
	private ListView allBundlesList = null;
	private TextView info;
	private AFelixSQLiteHelper afHelper = null;
	private Cursor cursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_bundle_list);
		
		allBundles = new ArrayList<HashMap<String, String>>();
		everyBundle = new HashMap<String, String>();
		
	    afHelper = new AFelixSQLiteHelper(getApplicationContext());
	    info = (TextView)findViewById(R.id.textView2);

	    allBundlesList = (ListView)findViewById(R.id.all_bundle_list);
	    
	    initDatabase();
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
			/*as.add("1");
			as.add("EnglishDictionary.jar");
			as.add(Environment.getExternalStorageDirectory().getPath() + "/Afelixdata/Bundle/");
			setTable("BundleLocationTable", as, null, MODE.INSERT);
			as.clear();*/
			as.add("BundleId");
			as.add("BundleName");
			as.add("BundleLocation");
			
			hs.put("BundleId", "INTEGER NOT NULL UNIQUE");
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
		}catch(SQLiteException se){
			//Toast.makeText(this, "The table isn't existing, create a new one.", Toast.LENGTH_LONG).show();
			Log.e(TAG, "The table is existing.", se);
		 
			as.add("1");
			as.add("EnglishDictionary.jar");
			as.add(Environment.getExternalStorageDirectory().getPath() + "/Afelixdata/Bundle/");
			setTable("BundleLocationTable", as, null, MODE.INSERT);
			as.clear();
		}
	}
	
	public ArrayAdapter<String> Query(String[] select, String table, String where){

		if(select != null){
			String wholeSelect = "";
			for(String s : select){
				wholeSelect += s;
			}
			
			if(where == null)
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
		
		//Toast.makeText(this, this.DATABASE_QUERY, Toast.LENGTH_LONG).show();
		Cursor cursor = afHelper.getReadableDatabase().rawQuery(DATABASE_QUERY, null);
		ArrayList<String> tempBundleList = new ArrayList<String>();
		
		while(cursor.moveToNext()){
			if(select == null){
				everyBundle.put("id", cursor.getString(0));
				everyBundle.put("BundleId", cursor.getString(1));
				everyBundle.put("BundleName", cursor.getString(2));
				everyBundle.put("BundleLocation", cursor.getString(3));
				
				tempBundleList.add(everyBundle.get("id") + "\t\t\t\t\t"
						+ everyBundle.get("BundleId") + "\t\t\t\t\t"
						+ everyBundle.get("BundleName") + "\t\t\t\t\t"
						+ everyBundle.get("BundleLocation" + "\t\t\t\t\t"));
			}else{
				
			}
			allBundles.add(everyBundle);
		}
		
		mArrayAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, 
				tempBundleList.toArray(new String[tempBundleList.size()]));
		allBundlesList.setAdapter(mArrayAdapter);
		return mArrayAdapter;
	}
	
	public void QueryTest(){
		Cursor cursor = afHelper.getReadableDatabase()
				.rawQuery("select * from BundleLocationTable", null);
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
				DATABASE_UPGRADE = "DROP TABLE IF EXISTS " + table;
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE);
				//db.execSQL(DATABASE_UPGRADE);
			break;
		case INSERT:
			DATABASE_UPGRADE = "insert into " + table + " values(null, ?, ?, ?)";
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE,
						column.toArray(new String[column.size()]));
				//QueryTest();
			}catch(SQLiteException se){
				Log.e(TAG, "Can't find the table to insert.", se);
				Toast.makeText(this, "Can't find the table to insert.", Toast.LENGTH_LONG).show();
			}

			Query(null, table, null);
			break;
		case DELETE:
			break;
		default:
			break;
		}
	}
}
