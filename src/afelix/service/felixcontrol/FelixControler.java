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
	
	public String install(Context context, String bundle, int command){
		Log.d(TAG, "About to install bundle: " + bundle);
		String res = this.MainControler(context, bundle, command);
		return res;
	}
	
	public String install(String bundle, String location, int command){
		Log.d(TAG, "About to install bundle: " + bundle);
		String res = this.MainControler(bundle, location, command);
		return res;
	}
	
	private String MainControler(Context context, String bundle, int command){
		switch(command){
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
		return "Success";
	}
	
	private String MainControler(String bundle, String location, int command){
		
		FileInputStream bs = null;
		
		try{
			Log.d(TAG, "Get bundle at: " + location);
			File f = null;
			
			//Escaping when users forget to input '/'
			if(location.charAt(location.length()-1) == '/')
				f = new File(location + bundle);
			else f = new File(location + "/" + bundle);
			
			bs = new FileInputStream(f);
		}catch(IOException ie){
			Log.e(TAG, "File open fail for " + ie.toString(), ie);
		}
		
		//Main command execution block
		switch(command){
			case 2:
				try{
					this.felixFramework.getBundleContext().installBundle(bundle, bs);
				}catch(BundleException be){
					Log.e(TAG, "Bundle "+ bundle + "installed fail for " + be.toString(), be);
					return "Bundle "+ bundle + "installed fail for " + be.toString();
				}
			case 32:
				break;
			default:
				return "Invalid command.";
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
