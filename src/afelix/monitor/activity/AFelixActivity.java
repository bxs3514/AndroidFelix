
/**
 * 
 * @author bxs3514
 *
 * This is a monitor to show the bundle states to users.
 *
 * @lastEdit 11/24/2014
 * 
 */

package afelix.monitor.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import afelix.afelixservice.androidfelix.R;
import afelix.service.controler.database.BundleDataCenter;
import afelix.service.interfaces.IAFelixService;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AFelixActivity extends ActionBarActivity implements OnClickListener{

	final private static String TAG = "AFelixActivity";
	
	private ServiceConnection mConnection = null;
	private IAFelixService mAFelixService = null;
	
	private ListView BundleList = null;
	private EditText Command = null;
	private TextView SystemInfo = null;
	private Button ConfirmBtn = null;
	private Button ResetBtn = null;
	private Button RefreshBtn = null;
	private Button ShowAllBundleBtn = null;
	private Intent bindServiceIntent = null;
	private Intent showBundleIntent = null;
	private Bundle getInstallBundle = null;

	private ArrayList installList = null;
	private ArrayList<String> bundleInstallList = new ArrayList<String>();
	private HashMap<Integer, String> installBundleMap = null;
	//private Iterator<String> it = null;
	private ArrayList<String> as = null;
	private ArrayAdapter<String> mArrayAdapter = null;
	private String[] bundles = null;
	private String[] operations = null;
	private Thread refreshThread = null;
	
	private RefreshList refresh = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afelix);
		//dbCenter = new BundleDataCenter();
		
		initViews();
		buildServiceConnection();

		bindServiceIntent = new Intent(IAFelixService.class.getName());
		
		bindServiceIntent = this.createExplicitFromImplicitIntent(
				getApplicationContext(), bindServiceIntent);

		bindService(bindServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
		//Toast.makeText(this, "OnCreate", Toast.LENGTH_LONG).show();
	}
	
	
	@Override
	protected void onStart(){
		super.onStart();
		
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		
	}
	
	
	@Override
	protected void onStop(){
		super.onStop();
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		if(mConnection != null)
			unbindService(mConnection);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		//Toast.makeText(this, requestCode + " " + resultCode + " ", Toast.LENGTH_LONG).show();
		switch (resultCode){
		case RESULT_OK:
			//Toast.makeText(this, "get", Toast.LENGTH_LONG).show();
			if(data.getExtras() != null){
				Bundle b = data.getExtras();
				
				getInstallBundle = data.getExtras();
				installList = getInstallBundle.getParcelableArrayList("installBundle");
				installBundleMap = (HashMap<Integer, String>) installList.get(0);
				//Toast.makeText(this, "" + installBundleMap.size(), Toast.LENGTH_LONG).show();
				for(Iterator i = installBundleMap.entrySet().iterator(); i.hasNext();){
					Map.Entry temp = (Map.Entry)i.next();
					//Toast.makeText(this, (String)temp.getValue(), Toast.LENGTH_LONG).show();
					bundleInstallList.add((String)temp.getValue());
					try {
						if((String)temp.getValue() != null && !((String)temp.getValue()).equals(""))
							mAFelixService.interpret("install " + ((String)temp.getValue()).split("\\s+")[1]);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						//Toast.makeText(this, "Command Wrong", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
				refresh.run();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.confirm:
			try {
				if(mAFelixService.interpret(Command.getText().toString())){
					//Refresh();
					refreshThread.run();
					Command.setText("");
				}
				//else 
					//Toast.makeText(AFelixActivity.this, "Your command is wrong!",
							//Toast.LENGTH_SHORT).show();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.refresh:
			//Refresh();
			refreshThread.run();
			break;
		case R.id.reset:
			Command.setText("");
			break;
		case R.id.showAllBundleList:
			showBundleIntent = new Intent(AFelixActivity.this, BundleDataCenter.class);
			this.startActivityForResult(showBundleIntent, 1);
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.afelix, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void buildServiceConnection(){
		
		mConnection = new ServiceConnection(){

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				try{
					if(service.getInterfaceDescriptor().equals(IAFelixService.class.getName())){
						mAFelixService = IAFelixService.Stub.asInterface(service);
						
						//Refresh();
						refreshThread.run();
					}
				}catch (RemoteException re){
					re.printStackTrace();
				}
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				Toast.makeText(AFelixActivity.this, "Service has unexpected disconnected", Toast.LENGTH_LONG).show();
				mAFelixService = null;
			}
			
		};
	}
	
	//Change a implicit intent to a explicit one
	private Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
 
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
 
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
 
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
 
        // Set the component to be explicit
        explicitIntent.setComponent(component);
 
        return explicitIntent;
    }
	
	private void initViews(){
		operations = new String[]{"Start", "Stop", "Uninstall"};
		BundleList = (ListView)findViewById(R.id.bundleList);
		
		BundleList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				// TODO Auto-generated method stub
				//final int finalPositon = position;
				final String bundle = (String)parent.getItemAtPosition(position);
				final String bundleId = (bundle.split("\\s+"))[0];
				//final String bundleName = (bundle.split("\\s+"))[1];
				
				//Toast.makeText(AFelixActivity.this, 
						//"The bundle is: " + bundleName, Toast.LENGTH_SHORT).show();
				
				new AlertDialog.Builder(AFelixActivity.this)
				.setTitle("Choose your operation to the bundle")
				.setItems(operations, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch(which){
						case 0:
							try {
								mAFelixService.interpret("start " + bundleId);
								refresh.run();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case 1:
							try {
								mAFelixService.interpret("stop " + bundleId);
								refresh.run();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case 2:
							try {
								mAFelixService.interpret("uninstall " + bundleId);
								refresh.run();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					}
				}).show();
				
			}
		});
		
		refresh = new RefreshList();
		refreshThread = new Thread(refresh);
		
		Command = (EditText)findViewById(R.id.command);
		Command.setOnEditorActionListener(new TextView.OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE
						|| (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())){
					try {
						if(mAFelixService.interpret(Command.getText().toString())){
							//Refresh();
							refreshThread.run();
							Command.setText("");
						}
						//else 
							//Toast.makeText(AFelixActivity.this, "Your command is wrong!", Toast.LENGTH_SHORT).show();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return true;
			}
			
		});
		
		ConfirmBtn = (Button)findViewById(R.id.confirm);
		ConfirmBtn.setOnClickListener(this);
		
		ResetBtn = (Button)findViewById(R.id.reset);
		ResetBtn.setOnClickListener(this);
		
		RefreshBtn = (Button)findViewById(R.id.refresh);
		RefreshBtn.setOnClickListener(this);
		
		ShowAllBundleBtn = (Button)findViewById(R.id.showAllBundleList);
		ShowAllBundleBtn.setOnClickListener(this);
	}
	
	
	private class RefreshList implements Runnable{
		
		private void Refresh(){
			try {
				as = (ArrayList<String>)mAFelixService.getAll();
				bundles = as.toArray(new String[as.size()]);
				mArrayAdapter = new ArrayAdapter<String>(AFelixActivity.this, 
						android.R.layout.simple_list_item_1, bundles);
				BundleList.setAdapter(mArrayAdapter);
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "Service has unexpected disconnected.", e);
				e.printStackTrace();
			}
		}
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Refresh();
		}
	}
	
	/*private void Refresh(){
		try {
			as = (ArrayList<String>)mAFelixService.getAll();
			bundles = as.toArray(new String[as.size()]);
			mArrayAdapter = new ArrayAdapter<String>(AFelixActivity.this, android.R.layout.simple_list_item_1, bundles);
			BundleList.setAdapter(mArrayAdapter);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Service has unexpected disconnected.", e);
			e.printStackTrace();
		}
	}*/


}
