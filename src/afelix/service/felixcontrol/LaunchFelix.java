
/**
 * 
 * @author bxs3514
 *
 * This is a android felix launcher.
 *
 * @lastEdit 11/9/2014
 * 
 */

package afelix.service.felixcontrol;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleException;

import android.util.Log;

public class LaunchFelix {
	private static final String TAG = "LaunchFelix";
	
	private Map<String, String> configMap = null;
	private Felix felixFramework = null;
	
	public Felix Launch(){
		
		String cacheDir = null;
    	try {
    		Log.d(TAG, "Create temp file");
			cacheDir = File.createTempFile("skifta", ".tmp").getParent();
			Log.d(TAG, "Done");
		}
    	catch (IOException e){
			Log.d(TAG, "unable to create temp file", e);
			return null;
		} 
    	
		Log.d(TAG, "Config the framework.");
		try{
			configMap = new HashMap<String, String>();
			configMap.put("org.osgi.framework.storage", cacheDir);
	        configMap.put("felix.embedded.execution", "true");
	        configMap.put("org.osgi.service.http.port", "9990");
	        configMap.put("org.osgi.framework.startlevel.beginning", "5");
		}catch(Exception e){
			Log.e(TAG,"Config fail:"+e.toString());
		}
		
		Log.d(TAG, "The framework is about to start..");
		try{
			
			this.felixFramework = new Felix(configMap);
			this.felixFramework.start();
			Log.d(TAG, "The felix framework start successfully.");
			
		}catch(Throwable ex){
			Log.e(TAG, "Couldn't create framework: " + ex.getMessage(), ex);
		}
		
		return felixFramework;
	}
	
}
