
/**
 * 
 * @author bxs3514
 *
 * This is the android felix manage service interface 
 * that expose to the user
 *
 * @lastEdit 11/10/2014
 * 
 */

package afelix.service.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleException;

import afelix.service.felixcontrol.FelixControler;
import afelix.service.felixcontrol.LaunchFelix;
import afelix.service.interfaces.BundlePresent;
import afelix.service.interfaces.IAFelixService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class AFelixAidlService extends Service{

	final private static String TAG = "FelixAidlService";
	
	private Felix main_felix_framework = null;
	private LaunchFelix launchFelix = null;
	private FelixControler fc = null;
	
	private ArrayList<String> info = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		launchFelix = new LaunchFelix(main_felix_framework);
		fc = new FelixControler(main_felix_framework);
		info = new ArrayList<String>();
		
		show("Service has been created.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return AFelixServiceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		AFelixServiceBinder = null;
	}
	
	private void show(final String s){
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
	private IAFelixService.Stub AFelixServiceBinder = new IAFelixService.Stub() {
		
		@Override
		public void installBundle(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			fc.install(AFelixAidlService.this, bundle,  2);
		}
		
		@Override
		public void installBundleByLocation(String bundle, String location)
				throws RemoteException {
			// TODO Auto-generated method stub
			fc.install(bundle, location, 2);
			show("Bundle:"+ bundle + "has been installed.");
		}
		
		@Override
		public void startFelix() throws RemoteException {
			// TODO Auto-generated method stub
			try {
				main_felix_framework.start();
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "Can't start felix for:"+e.toString(), e);
			}
		}
		

		@Override
		public void startBundle(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void stopBundle(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		
		@Override
		public void uninstallBundle(String bundle_id) throws RemoteException {
			// TODO Auto-generated method stub
			
		}


		@Override
		public String getAll() throws RemoteException {
			// TODO Auto-generated method stub

			String res = null;
			ArrayList<String> as =  fc.BundleInfo(main_felix_framework, 4);
			Iterator<String> it = as.iterator();
			
			while(it.hasNext()){
				res += it.next();
			}
			return "Fack!";
		}
		
		
		@Override
		public int getBundleId(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			return -1;
		}
		
		@Override
		public BundlePresent getBundlesContainer(String bundle)
				throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		@Override
		public String dependency(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
}
