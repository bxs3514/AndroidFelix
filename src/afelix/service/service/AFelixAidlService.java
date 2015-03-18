
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

import afelix.afelixservice.androidfelix.R;
import afelix.service.controler.felixcontrol.FelixControler;
import afelix.service.controler.felixcontrol.LaunchFelix;
import afelix.service.interfaces.BundlePresent;
import afelix.service.interfaces.IAFelixService;
import afelix.service.net.SocketTransfer;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AFelixAidlService extends Service{

	final private static String TAG = "FelixAidlService";
	
	private Felix main_felix_framework = null;
	private LaunchFelix launchFelix = null;
	private FelixControler fc = null;
	private BundlePresent bp;
	
	private TextView LogInfo;
	private String res = new String();
	
	private SocketTransfer mTrans;
	//private ArrayList<String> info = null;
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		launchFelix = new LaunchFelix();
		main_felix_framework = launchFelix.Launch();
		fc = new FelixControler(main_felix_framework, getApplicationContext());
		bp = new BundlePresent();
		
		mTrans = new SocketTransfer("192.168.100.2", 6666);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.activity_afelix, null);//Get the layout view
		LogInfo = (TextView)layout.findViewById(R.id.SystemInfo);//Get textview
		LogInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
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
				LogInfo.append("Can't start felix for:"+e.toString());
				Log.e(TAG, "Can't start felix for:"+e.toString(), e);
			}
		}
		
		@Override
		public void stopFelix() throws RemoteException {
			
			try {
				main_felix_framework.stop();
			} catch (BundleException e) {
				Log.e(TAG, "Can't stop felix for:"+e.toString(), e);
			}
		}
		
		
		@Override
		public void installBundle(String bundle) throws RemoteException {
			
			fc.install(AFelixAidlService.this, bundle, 2);
		}
		
		
		@Override
		public void installBundleByLocation(String bundle, String location)
				throws RemoteException {
			LogInfo.append(fc.install(bundle, location));
			//show(fc.install(bundle, location));
		}
		

		@Override
		public void startBundle(String bundle) throws RemoteException {
			
			//fc.start(bundle);
			//fc.start(bundle)
			show(fc.start(bundle));
		}
		
		@Override
		public void stopBundle(String bundle) throws RemoteException {
			
			show(fc.stop(bundle));
		}
		

		@Override
		public void resteartBundle(String bundle) throws RemoteException {
			show(fc.restart(bundle));			
		}
		


		@Override
		public void updateBundle(String bundle) throws RemoteException {
			show(fc.update(bundle));
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
			return bp;
		}
		
		
		@Override
		public String dependency(String bundle) throws RemoteException {
			
			return null;
		}


		@Override
		public boolean interpret(String command) throws RemoteException {
			String res = fc.interpret(getApplicationContext(), command);
			show(res);
			
			if(res.equals("Wrong command!")) return false;
			else return true;
		}


		@Override
		public BundlePresent executeBundle(BundlePresent bundle)
				throws RemoteException {
			Class<?>[] classArray = classListToArray(bundle.getClazz());
			
			bp = bundle;
			return fc.execute(getApplicationContext(), bundle,
					bundle.getPath(), bundle.getBundlePack(), 
					bundle.getClassName(), 
					bundle.getMethodName(),
					bundle.getResKey(),
					bundle.getParameter(), 
					classArray);
		}

		@Override
		public BundlePresent executeExistBundle(BundlePresent bundle)
				throws RemoteException {
			Class<?>[] classArray = classListToArray(bundle.getClazz());
			bp = bundle;
			return fc.execute(bundle, bundle.getMethodName(), 
					bundle.getResKey(), bundle.getParameter(), classArray);
		}

		@Override
		public void sendBundle(String bundle) throws RemoteException {
			mTrans.sendBundle(null, bundle);
		}

		@Override
		public void sendBundleOnPosition(String position, String bundle)
				throws RemoteException {
			mTrans.sendBundle(position, bundle);
			
		}
	};
	
	private Class<?>[] classListToArray(List<String> classList){
		Class<?>[] classArray = new Class<?>[classList.size()];
		int i = 0;
		for(Iterator<String> iterator = classList.iterator(); iterator.hasNext();){
			String tempClazz = iterator.next();
			try {
				classArray[i] = Class.forName(tempClazz);
			} catch (ClassNotFoundException e) {
				if(tempClazz.equals("boolean")) classArray[i] = boolean.class;
				else if(tempClazz.equals("byte")) classArray[i] = byte.class;
				else if(tempClazz.equals("short")) classArray[i] = short.class;
				else if(tempClazz.equals("int")) classArray[i] = int.class;
				else if(tempClazz.equals("long")) classArray[i] = long.class;
				else if(tempClazz.equals("float")) classArray[i] = float.class;
				else if(tempClazz.equals("double")) classArray[i] = double.class;
				else if(tempClazz.equals("char")) classArray[i] = char.class;
				else{
					Log.e(TAG, "Get Class Fail for: " + e.toString());
					e.printStackTrace();
				}
			} finally{
				i++;
			}
		}
		return classArray;
	}

	public SocketTransfer getmTrans() {
		return mTrans;
	}

	public void setmTrans(SocketTransfer mTrans) {
		this.mTrans = mTrans;
	}
	
	
}
