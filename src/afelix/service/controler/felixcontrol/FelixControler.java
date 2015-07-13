/**
 * 
 * @author bxs3514
 *
 * This is a android felix launcher.
 *
 * @lastEdit 11/23/2014
 * 
 */

package afelix.service.controler.felixcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import dalvik.system.DexClassLoader;
import afelix.service.controler.database.DatabaseControler;
import afelix.service.interfaces.BundlePresent;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class FelixControler implements BundleControler{
	final private static String TAG = "FelixControler";
	
	private Felix felixFramework;
	private Bundle resBundle;
	//private BundlePresent resBundlePresent;
	private ConsoleInterpreter mInterpreter;
	private Class<?> loadedClass;
	private ServiceReference<?> ref;
	private Object serviceInstance;
	
	private DatabaseControler mDbCtrl;
	
	private String res = new String();
	private boolean su = false;
	
	public FelixControler(Felix felixFramework, Context context){
		this.felixFramework = felixFramework;
		this.mInterpreter = new ConsoleInterpreter(this);
		
		mDbCtrl = new DatabaseControler(context);
	}
	
	@Override
	public String install(Context context, String bundle, int command){
		Log.d(TAG, "About to install bundle: " + bundle);
		String res = this.MainControler(context, bundle, command);
		return res;
	}
	
	@Override
	public String install(String bundle, String location){
		Log.d(TAG, "About to install bundle: " + bundle);
		res = this.MainControler(bundle, location, 2);
		return res;
	}
	
	@Override
	public String uninstall(String bundle){
		Log.d(TAG, "About to uninstall bundle: " + bundle);
		res = this.MainControler(bundle, "", 1);
		return res;
	}
	
	@Override
	public String start(String bundle){
		Log.d(TAG, "About to start bundle: " + bundle);
		res = this.MainControler(bundle, "", 32);
		return res;
	}
 
	@Override
	public String stop(String bundle){
		Log.d(TAG, "About to start bundle: " + bundle);
		res = this.MainControler(bundle, "", 16);
		return res;
	}

	
	@Override
	public String restart(String bundle) {
		// Restart a specific bundle
		Log.d(TAG, "About to start bundle: " + bundle);
		res = this.MainControler(bundle, "", 48);//stop code(16) + start code(32) = 48
		return res;
	}


	@Override
	public String update(String bundle) {
		// Update a specific bundle
		Log.d(TAG, "About to update bundle: " + bundle);
		res = this.MainControler(bundle, "", 64);
		return res;
	}


	@Override
	public String find(String bundle) {
		res = this.MainControler(bundle, "", 0);
		return res;
	}

	@Override
	public String dependency(String bundle) {
		res = this.MainControler(bundle, "", 128);
		System.out.println(res);
		return res;
	}
	
	public String interpret(Context context, String command){
		return mInterpreter.interpret(context, command);
	}

	public void setSu(boolean su) {
		this.su = su;
	}
	
	public Bundle getResBundle(String bundle){
		find(bundle);
		return resBundle;
	}
	
	private String MainControler(String bundle, String location, int command){
		//Main command execution block
		
		switch(command){
		case 2://install bundle
			FileInputStream bs = null;
			if(location == null){
				//location = Environment.getExternalStorageDirectory().getPath() + 
						//File.separator + "AFelixData" + File.separator + "Bundle";
				location = mInterpreter.getDefaultPath();
			}
			try{
				Log.d(TAG, "Get bundle at: " + location);
				File f = null;
					
				//Escaping when users forget to input file separator
				if(location.charAt(location.length()-1) == File.separatorChar)
					f = new File(location + bundle);
				else f = new File(location + File.separatorChar + bundle);
				
				bs = new FileInputStream(f);
			}catch(IOException ie){
				Log.e(TAG, "File open fail for " + ie.toString(), ie);
				return "File open failed.";
			}
			
			try{
				org.osgi.framework.Bundle mBundle = this.felixFramework.getBundleContext().installBundle(bundle, bs);
				ArrayList<String> as = new ArrayList<String>();
				as.add(mBundle.getSymbolicName());
				as.add(bundle);
				//if(mDbCtrl.Query(as.toArray(new String[as.size()]), "File", "Bundle = '" + mBundle.getSymbolicName() + "'").size() == 0)
					this.mDbCtrl.Insert("File", as);
				//else this.mDbCtrl.Update("File", as);
				//new Thread(){
					//public void run(){
						//this.mDbCtrl.Insert("File", new ArrayList<String>(Arrays.asList(mBundle, bundle)));
					//}
				//}.start();
			}catch(BundleException be){
				Log.e(TAG, "Bundle "+ bundle + "installed fail for " + be.toString(), be);
				return "Bundle "+ bundle + "installed fail for " + be.toString();
			}
				
			try{
				bs.close();
			}catch(IOException ie){
				Log.e(TAG, "Close the inputstream fail for " + ie.toString(), ie);
				return "Fail to close the file.";
			}
			
			return "Bundle:" + bundle + " has installed successfully";
		case 0://find bundle
		case 1://uninstall a bundle
		case 16://stop a bundle
		case 32://start a bundle
		case 48://restart a bundle
		case 64://update a bundle
		case 128://bundle dependency
			long bid = -1;
			boolean isLong = false;
			
			for(int i = 0; i < bundle.length(); i++){
				Log.d(TAG, String.valueOf(bundle.charAt(i)));
				if(bundle.charAt(i) < '0' || bundle.charAt(i) > '9') break;
				if(i == bundle.length() - 1){
					bid = Long.parseLong(bundle);
					if(bid == 0 && !su) {
						resBundle = felixFramework.getBundleContext().getBundle(0);
						return "Permission denied.";
					}
					isLong = true;
				}
			}
			
			if(!isLong){
				org.osgi.framework.Bundle[] bl = felixFramework.getBundleContext().getBundles();
		        for (int i = 0; bl != null && i < bl.length; i++) {
		        	org.osgi.framework.Bundle tempBundle = bl[i];
		        	Log.d(TAG, bundle+": "+tempBundle.getBundleId());
		            if (bundle.equals(tempBundle.getBundleId()) || bundle.equals(tempBundle.getLocation())  
		            		|| bundle.equals(tempBundle.getSymbolicName())) {
		                bid = tempBundle.getBundleId();
		            }
		        }
			}

	        org.osgi.framework.Bundle b = felixFramework.getBundleContext().getBundle(bid);
	        if (b == null) {
	        	Log.e(TAG, "The bundle " + bundle + " doesn't exist.");
	            return "The bundle " + bundle + " doesn't exist.";
	        }
	        
	        //ServiceTracker st = null;
	        switch(command){
	        case 0:
	        	resBundle = b;
	        	return "Bundle: " + b.getSymbolicName() + " is found."; 
	        case 1:
	        	try {
		            b.uninstall();
		            Log.d(TAG, "bundle: " + b.getSymbolicName() + "/" + b.getBundleId() + "/"
		                    + b + " has uninstalled from felix.");
		            return "Bundle: " + bundle + " has installed successfully";
		        } catch (BundleException be) {
		        	Log.e(TAG, be.toString(), be);
		        	return "Unable to uninstall Bundle: " + bundle + " for\n" + be.toString();
		        }
	        case 16:
	        	try {
		            b.stop(org.osgi.framework.Bundle.RESOLVED);
		            
		            Log.d(TAG, "bundle: " + b.getSymbolicName() + "/" + b.getBundleId() + "/"
		                    + b + " stopped");
		        	return "Bundle: " + bundle + " has stoped successfully";
		        } catch (BundleException be) {
		        	Log.e(TAG, be.toString(), be);
		        	return "Unable to stop Bundle: " + bundle + " for\n" + be.toString();
		        }
	        case 32:
	        	try {
		            b.start(org.osgi.framework.Bundle.START_ACTIVATION_POLICY);
		            Log.d(TAG, "bundle: " + b.getSymbolicName() + "/" + b.getBundleId() + "/"
		                    + b + " started");
		            return "Bundle: " + bundle + " has started successfully";
		        } catch (BundleException be) {
		        	System.out.println(be.toString());
		        	be.getStackTrace();
		        	return "Unable to start Bundle: " + bundle + " for\n" + be.toString();
		        }
	        case 48:
	        	try {
	        		b.stop(org.osgi.framework.Bundle.RESOLVED);
	        		b.start(org.osgi.framework.Bundle.START_ACTIVATION_POLICY);
	        		Log.d(TAG, "bundle: " + b.getSymbolicName() + "/" + b.getBundleId() + "/"
		                    + b + " restarted");
	        		return "Bundle: " + bundle + " has restarted successfully";
	        	}catch (BundleException be) {
		        	System.out.println(be.toString());
		        	return "Unable to restart Bundle: " + bundle + " for\n" + be.toString();
		        }
	        case 64:
	        	try{
	        		b.update();
	        		Log.d(TAG, "bundle: " + b.getSymbolicName() + "/" + b.getBundleId() + "/"
		                    + b + " updated");
	        		return "Bundle: " + bundle + " has updated successfully";
	        	}catch (BundleException be) {
		        	System.out.println(be.toString());
		        	return "Unable to restart Bundle: " + bundle + " for\n" + be.toString();
		        }
	        case 128:
	        	/*URL manifestUrl = b.getEntry("META-INF/MANIFEST.MF");
	        	try {
					BufferedReader manifestReader = new BufferedReader(
							new InputStreamReader(manifestUrl.openConnection().getInputStream()));
					String manifestText = "";
					String temp = "";
					boolean ifDependency = false;
					while(manifestReader.ready()){
						temp = manifestReader.readLine();
						if(temp != null){
							if(temp.indexOf("Import-Package") == 0){
								manifestText += temp.split(": ")[1];
								ifDependency = true; 
							}else if(temp.contains(":") && ifDependency){
								break;
							}else if(ifDependency){
								manifestText += temp;
							}
						}
					}
					
					System.out.println(manifestText + manifestText.length());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "Unable to get the Bundle:" + bundle + "'s dependency for\n" + e.toString();
				}*/
		        return b.getHeaders().get("Import-Package");
	    
	        }
		default:
			return "Invalid command.";   
		}
	}
	
	private String MainControler(Context context, String bundle, int command){
		return null;
		/*switch(command){
			case 2:
				InputStream bs = null;
				
				try{
					bs =  context.getAssets().open("bundle/" + bundle);
				}catch(IOException ie){
					Log.e(TAG, "Get assests inputstream fail for " + ie.toString(), ie);
					return "Fail";
				}
				
				try{
					this.felixFramework.getBundleContext().installBundle(bundle, bs);
				}catch(BundleException be){
					Log.e(TAG, "Bundle "+ bundle + "installed fail for " + be.toString(), be);
					return "Fail";
				}
				
				try{
					bs.close();
				}catch(IOException ie){
					Log.e(TAG, "Close the inputstream fail for " + ie.toString(), ie);
					return "Fail";
				}
				break;
			case 32:
				break;
			default:
				return "Invalid command.";
		}
		return "Success";*/
	}
	
	@Override
	public ArrayList<String> BundleInfo(int command){
		ArrayList<String> as = new ArrayList<String>();
		
		for(org.osgi.framework.Bundle b : felixFramework.getBundleContext().getBundles()) {
			switch(command){
			case 1:
				as.add(Long.toString(b.getBundleId()));
				break;
			case 2:
				as.add(b.getSymbolicName());
				break;
			case 4:
				switch(b.getState()){
				case 2:
					as.add(b.getBundleId()+" \t\t" + b.getSymbolicName() + "\t\t INSTALLED");
					break;
				case 4:
					as.add(b.getBundleId()+" \t\t" + b.getSymbolicName() + "\t\t RESOLVED");
					break;
				case 8:
					as.add(b.getBundleId()+" \t\t" + b.getSymbolicName() + "\t\t STARTING");
					break;
				case 16:
					as.add(b.getBundleId()+" \t\t" + b.getSymbolicName() + "\t\t STOPPING");
					break;
				case 32:
					as.add(b.getBundleId()+" \t\t" + b.getSymbolicName() + "\t\t ACTIVE");
				}
				break;
			default:
				Log.e(TAG, "Invalid command.");
			}
		}
		return as;
	}
	
	/**
	 * Calling agent bundle to execute and get executing result
	 * 
	 * @param path: the bundle(jar) package path
	 * @param bundlePack: package name
	 * @param className: class name of the package
	 * @param methodName: deploy method
	 * @param resKey: hash key, used to write result into bundlepresent
	 * @param parameter: parameters
	 * @param clazz: class type of parameters
	 * 
	 */
	@SuppressLint("NewApi")
	@Override
	public BundlePresent execute(Context context, BundlePresent mBundle, 
			String path, String bundlePack, String className, String methodName, 
			String resKey, Object[] parameters, Class<?>...clazz) {
		// Get result from agent bundle which in assets

		Log.d(TAG, "Executing...");
		if(context == null && mBundle.getPath() == null){
			Log.e(TAG, "Don't set both context and path to null!");
			return null;
		}

		BundleContext mBundlecontext = felixFramework.getBundleContext();
		try {
			//Log.d(TAG, bundlePack);
			File tempFile = null;
			
			if(context != null){
				InputStream bundleStream = context.getAssets().open(bundlePack);
				tempFile = File.createTempFile("temp", ".jar");
				FileOutputStream fup = new FileOutputStream(tempFile);
				
				int read = 0;
				byte[] buffer = new byte[65536];
				
				while((read = bundleStream.read(buffer)) != -1){
					fup.write(buffer, 0, read);
				}
				bundleStream.close();
				fup.close();
			}
			//else if(path != null){
				//tempFile = new File(path);
			//}
			
			try{
				File dexFile = context.getApplicationContext().getDir("bundleDex", 0);
				DexClassLoader classLoader = new DexClassLoader(
						tempFile.getAbsolutePath(), dexFile.getAbsolutePath(),
						null, context.getApplicationContext().getClassLoader());
				
				loadedClass = classLoader.loadClass(className);

				ref = mBundlecontext.getServiceReference(
						loadedClass.getName());
				if(ref != null){
					loadedClass = mBundlecontext.getService(ref).getClass();
					serviceInstance = (Object)mBundlecontext.getService(ref);
					
					Method m = loadedClass.getMethod(mBundle.getMethodName(), clazz);
					mBundle.setBundleResult(resKey, m.invoke(serviceInstance, parameters));
				}
				else{
					Log.e(TAG, "Null serveice!");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mBundle;
	}

	@Override
	public BundlePresent execute(BundlePresent mBundle, String methodName,
			String resKey, Object[] parameters, Class<?>... clazz) {
		try {
			Method m = loadedClass.getMethod(mBundle.getMethodName(), clazz);
			mBundle.setBundleResult(resKey, m.invoke(serviceInstance, parameters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBundle;
	}

}
