
/**
 * 
 * @author bxs3514
 *
 * This is a monitor to show the bundle states to users.
 *
 * @lastEdit 11/18/2014
 * 
 */


package afelix.mornitor.activity;

import afelix.afelixservice.androidfelix.R;
import afelix.service.interfaces.IAFelixService;
import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class AFelixActivity extends ActionBarActivity implements OnClickListener{

	final private static String TAG = "AFelixActivity";
	
	private ServiceConnection mConnection = null;
	private IAFelixService mAFelixService = null;
	
	private TextView BundleInfo = null;
	private EditText Command = null;
	private Button Confirm = null;
	private Button Refresh = null;
	private Intent intent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afelix);
		
		initViews();
		buildServiceConnection();
		
		intent = new Intent(IAFelixService.class.getName());
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		if(mConnection != null)
			unbindService(mConnection);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.confirm:
			try {
				if(mAFelixService.interpret(Command.getText().toString())){
					Refresh();
					Command.setText("");
				}
				else 
					Toast.makeText(AFelixActivity.this, "Your command is wrong!", Toast.LENGTH_SHORT).show();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.refresh:
			Refresh();
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
						//mAFelixService.installBundleByLocation("Helloosgi_1.0.0.jar", "/sdcard/bundle/");
						
						String s = mAFelixService.getAll();
						BundleInfo.append(s);
						   
						//mAFelixService.uninstallBundle("Helloosgi_1.0.0.jar");
						//Refresh();
						//Toast.makeText(AFelixActivity.this, s, Toast.LENGTH_LONG).show();
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
	
	private void initViews(){
		BundleInfo =  (TextView)findViewById(R.id.bundleInfo);
		BundleInfo.setText("Id\t\t\t\t Name\t\t\t\t\t\t\t\t\t\t Status\t \n");
		Command = (EditText)findViewById(R.id.command);
		Command.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				try {
					if(mAFelixService.interpret(Command.getText().toString())){
						Refresh();
						Command.setText("");
					}
					else 
						Toast.makeText(AFelixActivity.this, "Your command is wrong!", Toast.LENGTH_SHORT).show();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return true;
			}
			
		});
		
		Confirm = (Button)findViewById(R.id.confirm);
		Confirm.setOnClickListener(this);
		Refresh = (Button)findViewById(R.id.refresh);
		Refresh.setOnClickListener(this);
	}
	
	private void Refresh(){
		try {
			String s = mAFelixService.getAll();
			BundleInfo.setText("Id\t\t\t\t Name\t\t\t\t\t\t\t\t\t\t Status\t \n" + s);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Service has unexpected disconnected.", e);
			e.printStackTrace();
		}
	}


}
