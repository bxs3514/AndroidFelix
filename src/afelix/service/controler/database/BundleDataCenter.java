/**
 * 
 * @author bxs3514
 *
 * This is android felix launcher.
 *
 * @lastEdit 12/6/2014
 * 
 */

package afelix.service.controler.database;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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


public class BundleDataCenter extends Activity implements OnClickListener{
	private static final String TAG = "DataControler";
	
	private ArrayList<HashMap<String, String>> allBundles;
	private ArrayList<String> bundleLocation;
	private ArrayList installBundles;
	private HashMap<Integer, String> selectedBundles;
	private int selectNumber;
	private int totalNumber;
	
	private ArrayAdapter<String> mArrayAdapter;
	private ListView allBundlesList;
	private TextView info;
	private DatabaseControler dbCtrl;
	
	private FileControler mFileControler;
	private File[] bundleFiles;
	
	private Bundle installAndroidBundle;
	private Intent installIntent;
	private Button installBundleBtn;
	//private Button fileSystemBtn = null;
	//private Button SelectAllBtn = null;
	//private Button DeselectBtn = null;
	
	ArrayList<String> list = new ArrayList<String>();//Change later!
	
	public BundleDataCenter(){

		bundleLocation = new ArrayList<String>();
		installBundles = new ArrayList<HashMap<Integer, String>>();
		new HashMap<String, String>();
		selectedBundles = new HashMap<Integer, String>();
		selectNumber = 0;
		totalNumber = 0;
		
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.install:
			dbCtrl.closeDatabase();
			installAndroidBundle = new Bundle();
			installIntent = new Intent(this, AFelixActivity.class);
			//Toast.makeText(this, "Start install", Toast.LENGTH_LONG).show();
			
			installBundles.add(selectedBundles);
			installAndroidBundle.putParcelableArrayList("installBundle", installBundles);
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
	
	private void initData(){
		
		initControl();
		
		ArrayList<String> as = new ArrayList<String>();
		for(int i = 0; i < selectedBundles.size(); i++){
			selectedBundles.put(i, "");
		}
		
		mFileControler = new FileControler(bundleLocation.get(0));
		ArrayList<String> tempBundleInfo = new ArrayList<String>();
		
		
		if(bundleLocation.size() > 0){//The same!!!
			String tempBundleLocation = mFileControler.getLocation();
			bundleFiles = mFileControler.getFileList(tempBundleLocation, null);
			//Toast.makeText(this, tempBundleLocation, Toast.LENGTH_LONG).show();
			if(bundleFiles != null){
				for(File f : bundleFiles){
					as.add(f.getName());
					as.add(f.getParent());
					dbCtrl.Insert("Bundle", as);
					as.clear();
				}
			}else{
				Toast.makeText(this, "No Bundles!", Toast.LENGTH_LONG).show();
				Log.e(TAG, "No Bundles!");
			} 
		}else{
			
		}
		
		HashMap<String, String> tempHashMap = new HashMap<String, String>();
		//if(allBundles != null)
			//info.setText(allBundles.get(1).get("Name"));
		//info.setText("");
		for(Iterator<HashMap<String, String>> bundleIt = allBundles.iterator(); bundleIt.hasNext(); ){
			tempHashMap = (HashMap<String, String>) bundleIt.next();
			//for(Iterator<Entry<String, String>> bundleInfoIt = tempHashMap.entrySet().iterator(); bundleInfoIt.hasNext(); ){
				//Map.Entry<String, String> bundleInfoEntry = (Map.Entry<String, String>)bundleInfoIt.next();
			//Toast.makeText(this, "!!!", Toast.LENGTH_LONG).show();
			//info.append(tempHashMap.get("Name"));
			tempBundleInfo.add(tempHashMap.get("id") 
					+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + tempHashMap.get("Name"));
			//}
			
		}
		//tempHashMap.clear();
		totalNumber = tempBundleInfo.size();
		mArrayAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_checked, 
				tempBundleInfo.toArray(new String[tempBundleInfo.size()]));
		allBundlesList.setAdapter(mArrayAdapter);
		allBundlesList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
	}
	
	private void initControl(){
		dbCtrl = new DatabaseControler(getApplicationContext());
		allBundles = dbCtrl.Query(null, "Bundle", null);

	    info = (TextView)findViewById(R.id.bundle_select_number);
		info.setText("Selected number: 0 \nTotal bundles: " + allBundles.size());
		//afHelper = new AFelixSQLiteHelper(getApplicationContext());
		
		//Toast.makeText(this, String.valueOf(afHelper.a), Toast.LENGTH_LONG).show();
		
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
					selectNumber++;
				}else{
					//Toast.makeText(BundleDataCenter.this, 
							//"The bundle: " + (bundle.split("\\s+"))[1] + " is deselected.", Toast.LENGTH_SHORT).show();
					selectedBundles.remove(position);
					selectNumber--;
				}
				info.setText("Selected number: " + selectNumber
						+ "\nTotal bundles: " + allBundles.size());
			}
			
		});
		
		installBundleBtn = (Button)findViewById(R.id.install);
		installBundleBtn.setOnClickListener(this);
		//fileSystemBtn = (Button)findViewById(R.id.select_all);
		//fileSystemBtn.setOnClickListener(this);
	}
	
}
