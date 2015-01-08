
/**
 * 
 * @author bxs3514
 *
 * This is the android felix manage service interface 
 * that expose to users
 *
 * @lastEdit 1/5/2015
 * 
 */

package afelix.service.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleException;

import afelix.service.controler.felixcontrol.FelixControler;
import afelix.service.controler.felixcontrol.LaunchFelix;
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
	private BundlePresent bp;
	
	String res = new String();
	//private ArrayList<String> info = null;
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		launchFelix = new LaunchFelix();
		main_felix_framework = launchFelix.Launch();
		fc = new FelixControler(main_felix_framework);
		bp = new BundlePresent();
		//info = new ArrayList<String>();
		
		Log.d(TAG, "Service has been created.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		
		return AFelixServiceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		
		AFelixServiceBinder = null;
	}
	
	private void show(final String s){
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){

			@Override
			public void run() {
				
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
	private IAFelixService.Stub AFelixServiceBinder = new IAFelixService.Stub() {
		
		@Override
		public void startFelix() throws RemoteException {
			
			try {
				main_felix_framework.start();
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "Can't start felix for:"+e.toString(), e);
			}
		}
		

		@Override
		public void stopFelix() throws RemoteException {
			
			try {
				main_felix_framework.stop();
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "Can't stop felix for:"+e.toString(), e);
			}
		}
		
		
		@Override
		public void installBundle(String bundle) throws RemoteException {
			
			fc.install(AFelixAidlService.this, bundle,  2);
		}
		
		
		@Override
		public void installBundleByLocation(String bundle, String location)
				throws RemoteException {
			
			show(fc.install(bundle, location));
		}
		

		@Override
		public void startBundle(String bundle) throws RemoteException {
			
			//fc.start(bundle);
			show(fc.start(bundle));
		}
		
		@Override
		public void stopBundle(String bundle) throws RemoteException {
			
			show(fc.stop(bundle));
		}
		
		
		@Override
		public void uninstallBundle(String bundle_id) throws RemoteException {
			
			show(fc.uninstall(bundle_id));
		}


		@Override
		public List<String> getAll() throws RemoteException {
			

			//String res = new String();
			ArrayList<String> as =  fc.BundleInfo(4);
			//Iterator<String> it = as.iterator();
			
			//while(it.hasNext()){
				//res += it.next() + "\n";
			//}
			return as;
		}
		
		
		@Override
		public int getBundleId(String bundle) throws RemoteException {
			
			ArrayList<String> as = fc.BundleInfo(2);
			Iterator<String> it = as.iterator();
			
			while(it.hasNext()){
				res += it.next() + "\n";
			}
			return Integer.parseInt(res);
		}
		
		@Override
		public BundlePresent getBundlesContainer(String bundle)
				throws RemoteException {
			
			bp.setResBundle(fc.getResBundle(bundle));
			return bp;
		}
		
		
		@Override
		public String dependency(String bundle) throws RemoteException {
			
			return null;
		}


		@Override
		public boolean interpret(String command) throws RemoteException {
			String res = fc.interpret(command);
			show(res);
			
			if(res.equals("Wrong command!")) return false;
			else return true;
		}

	};
	
}
