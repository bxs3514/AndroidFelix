package afelix.service.felixcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleException;

import android.content.Context;
import android.util.Log;

public class FelixControler {
	final private static String TAG = "FelixControler";
	
	Felix felixFramework = null;
	
	public FelixControler(Felix f){
		this.felixFramework = f;
	}
	
	public String installBundle(String bundle, Context context){
		Log.d(TAG, "About to install bundle: " + bundle);
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
		
		return "Success";
	}
	
	public String installBundle(String bundle, String location){
		Log.d(TAG, "About to install bundle: " + bundle);
		FileInputStream bs = null;
		
		try{
			Log.d(TAG, "Get bundle at: " + location);
			File f = null;
			if(location.charAt(location.length()-1) == '/')
				f = new File(location + bundle);
			else f = new File(location + "/" + bundle);
			
			bs = new FileInputStream(f);
			this.felixFramework.getBundleContext().installBundle(bundle, bs);
		}catch(Exception e){
			Log.e(TAG, "Bundle "+ bundle + "installed fail for " + e.toString(), e);
			return "Fail";
		}
		
		try{
			bs.close();
		}catch(IOException ie){
			Log.e(TAG, "Close the inputstream fail for " + ie.toString(), ie);
			return "Fail";
		}
		
		return "Success";
	}
	
	
	
}
