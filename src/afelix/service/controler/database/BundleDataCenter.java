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
import afelix.monitor.activity.AFelixActivity;
import afelix.service.controler.file.FileControler;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


enum MODE{
	ADD, DROP, DELETE, INSERT
};

public class BundleDataCenter extends Activity implements IDatabaseControler, OnClickListener{
	private static final String TAG = "DataControler";
	
	private static final String TABLE_COLUMN_ID = "_id";
	private String DATABASE_UPGRADE = "";
	private String DATABASE_QUERY = "";
	
	private ArrayList<String> bundleLocation = null;
	private ArrayList allBundles = null;
	private HashMap<String, String> everyBundle = null;
	private HashMap<Integer, String> selectedBundles = null;
	private int SelectNumber;
	private int TotalNumber;
	
	private ArrayAdapter<String> mArrayAdapter = null;
	private ListView allBundlesList = null;
	private TextView info;
	private AFelixSQLiteHelper afHelper = null;
	private Cursor cursor = null;
	
	private FileControler mFileControler = null;
	private File[] bundleFiles = null;
	
	private Bundle installAndroidBundle;
	private Intent installIntent;
	private Button installBundleBtn = null;
	//private Button fileSystemBtn = null;
	//private Button SelectAllBtn = null;
	//private Button DeselectBtn = null;
	
	ArrayList<String> list = new ArrayList<String>();//Change later!
	
	public BundleDataCenter(){

		bundleLocation = new ArrayList<String>();
		allBundles = new ArrayList<HashMap<Integer, String>>();
		everyBundle = new HashMap<String, String>();
		selectedBundles = new HashMap<Integer, String>();
		SelectNumber = 0;
		TotalNumber = 0;
		
		bundleLocation.add(Environment.getExternalStorageDirectory().getPath() 
				+ "/Afelixdata/Bundle/");
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_bundle_list);
		
	    initData();
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
		case R.id.install:
			installAndroidBundle = new Bundle();
			installIntent = new Intent(this, AFelixActivity.class);
			//Toast.makeText(this, "Start install", Toast.LENGTH_LONG).show();
			
			allBundles.add(selectedBundles);
			installAndroidBundle.putParcelableArrayList("installBundle", allBundles);
			installIntent.putExtras(installAndroidBundle);
			//startActivity(installIntent);
			BundleDataCenter.this.setResult(RESULT_OK, installIntent);
			BundleDataCenter.this.finish();
			break;
		//case R.id.select_all:
			//for(Iterator i = selectedBundles.entrySet().iterator(); i.hasNext(); ){
				//mArrayAdapter.ge
			//}
			//Refresh();
			//break;
		//case R.id.deselect:
			//break;
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
	
	private void initControl(){
		
		afHelper = new AFelixSQLiteHelper(getApplicationContext());
		
		//Toast.makeText(this, String.valueOf(afHelper.a), Toast.LENGTH_LONG).show();
		
	    info = (TextView)findViewById(R.id.bundle_select_number);
		
	    allBundlesList = (ListView)findViewById(R.id.all_bundle_list);
		allBundlesList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				String bundle = (String)parent.getItemAtPosition(position);
				if(selectedBundles.get(position) == null || selectedBundles.get(position).equals("")){
					//Toast.makeText(BundleDataCenter.this, 
							//"The bundle: " + bundle + " is selected.", Toast.LENGTH_SHORT).show();
					
					selectedBundles.put(position, bundle);
					SelectNumber++;
				}else{
					//Toast.makeText(BundleDataCenter.this, 
							//"The bundle: " + (bundle.split("\\s+"))[1] + " is deselected.", Toast.LENGTH_SHORT).show();
					selectedBundles.put(position, "");
					SelectNumber--;
				}
				info.setText("Selected number: " + SelectNumber);
						//+ "\nTotal bundles: " + selectedBundles.size());
			}
			
		});
		
		installBundleBtn = (Button)findViewById(R.id.install);
		installBundleBtn.setOnClickListener(this);
		//fileSystemBtn = (Button)findViewById(R.id.select_all);
		//fileSystemBtn.setOnClickListener(this);
	}
	
	private void initData(){
		
		initControl();
		
		ArrayList<String> as = new ArrayList<String>();
		HashMap<String, String> hs = new HashMap<String, String>();
		
		for(int i = 0; i < selectedBundles.size(); i++){
			selectedBundles.put(i, "");
		}
		
		mFileControler = new FileControler(bundleLocation.get(0));
		
		dropTable("BundleLocationTable");
		
		String tempBundleLocation;
		try{
			//as.add("BundleId");
			as.add("BundleName");
			as.add("BundleLocation");
			
			//hs.put("BundleId", "INTEGER NOT NULL UNIQUE");
			hs.put("BundleName", "VARCHAR(255) UNIQUE");
			hs.put("BundleLocation", "VARCHAR(255)");
			
			addTable("BundleLocationTable", as, hs);
			as.clear();
			hs.clear();
			
			//as.add("1");
			
			if(bundleLocation.size() >= 1){//add iterator to bundlelocation!!!
				tempBundleLocation = mFileControler.getLocation();
				bundleFiles = mFileControler.getFileList(tempBundleLocation, null);
				//Toast.makeText(this, tempBundleLocation, Toast.LENGTH_LONG).show();
				if(bundleFiles != null){
					for(File f : bundleFiles){
						as.add(f.getName());
						as.add(f.getParent());
						Insert("BundleLocationTable", as);
						as.clear();
					}
				}else{
					Toast.makeText(this, "No Bundles!", Toast.LENGTH_LONG).show();
					Log.e(TAG, "No Bundles!");
				}
			}else{
				//The methods used to get bundles from lots of positions.
			}
		}catch(SQLiteException se){
			//Toast.makeText(this, "The table isn't existing, create a new one.", Toast.LENGTH_LONG).show();
			Log.e(TAG, "The table is existing.", se);
		 
			//as.add("1");
			if(bundleLocation.size() >= 1){//The same!!!
				tempBundleLocation = mFileControler.getLocation();
				bundleFiles = mFileControler.getFileList(tempBundleLocation, null);
				//Toast.makeText(this, tempBundleLocation, Toast.LENGTH_LONG).show();
				if(bundleFiles != null){
					for(File f : bundleFiles){
						as.add(f.getName());
						as.add(f.getParent());
						Insert("BundleLocationTable", as);
						as.clear();
					}
				}else{
					Toast.makeText(this, "No Bundles!", Toast.LENGTH_LONG).show();
					Log.e(TAG, "No Bundles!");
				} 
			}else{
				
			}
		}
	}
	
	public ArrayList<String> Query(String[] select, String table, String where){
		ArrayList<String> as = new ArrayList<String>();
		
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
		cursor = afHelper.getReadableDatabase().rawQuery(DATABASE_QUERY, null);
		ArrayList<String> tempBundleList = new ArrayList<String>();
		
		while(cursor.moveToNext()){
			if(select == null){
				everyBundle.put("id", cursor.getString(0));
				//everyBundle.put("BundleId", cursor.getString(1));
				everyBundle.put("BundleName", cursor.getString(1));
				everyBundle.put("BundleLocation", cursor.getString(2));
				
				tempBundleList.add(everyBundle.get("id") + 
						"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + everyBundle.get("BundleName"));
				
				TotalNumber++;
			}else{
				//Functions when select some elements
				if(select.equals("BundleLocation")){
					as.add(cursor.getString(0));
					
				}
			}
			//allBundles.add(everyBundle);
		}
		
		mArrayAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_checked, 
				tempBundleList.toArray(new String[tempBundleList.size()]));
		allBundlesList.setAdapter(mArrayAdapter);
		allBundlesList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		return as;
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
			//info.setText(afHelper.getReadableDatabase().toString());
			
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
			DATABASE_UPGRADE = "insert into " + table + " values(null, ?, ?)";
			try{
				afHelper.getReadableDatabase().execSQL(DATABASE_UPGRADE,
						column.toArray(new String[column.size()]));
				
			}catch(SQLiteException se){
				Log.e(TAG, "Can't find the table to insert.", se);
				//Toast.makeText(this, "Can't find the table to insert.", Toast.LENGTH_LONG).show();
			}
			//QueryTest();
			Query(null, table, null);
			break;
		case DELETE:
			break;
		default:
			break;
		}
	}
	
	/*public void Refresh(){
		
		mArrayAdapter.notifyDataSetChanged();
		
		info.setText("Selected number: " + SelectNumber);
	}
	
	public void QueryTest(){
		Cursor cursor = afHelper.getReadableDatabase()
				.rawQuery("select * from BundleLocationTable", null);
		if (cursor.moveToFirst() == false){
			             .makeText(this, "Empty!", Toast.LENGTH_LONG).show();
		}else{
			
			Toast.makeText(this, cursor.getString(2), Toast.LENGTH_LONG).show();
		}
		//info.setText(cursor.getString(0));
	}*/
	
}
