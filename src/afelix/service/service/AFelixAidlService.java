
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

import org.apache.felix.framework.Felix;

import afelix.service.felixcontrol.FelixControler;
import afelix.service.felixcontrol.LaunchFelix;
import afelix.service.interfaces.BundlePresent;
import afelix.service.interfaces.IAFelixService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AFelixAidlService extends Service{

	private Felix main_felix_framework = null;
	private LaunchFelix launchFelix = null;
	private FelixControler fc = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		launchFelix = new LaunchFelix(main_felix_framework);
		fc = new FelixControler(main_felix_framework);
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
	
	private IAFelixService.Stub AFelixServiceBinder = new IAFelixService.Stub() {
		
		@Override
		public void installBundle(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			fc.installBundle(bundle, AFelixAidlService.this);
		}
		
		@Override
		public void installBundleByLocation(String bundle, String location)
				throws RemoteException {
			// TODO Auto-generated method stub
			fc.installBundle(bundle, location);
		}
		
		@Override
		public void startFelix() throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		
		
		@Override
		public void stopBundle(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		
		@Override
		public void startBundle(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void uninstallBundle(String bundle_id) throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public BundlePresent getBundlesContainer(String bundle)
				throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getBundleId(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String dependency(String bundle) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
}
