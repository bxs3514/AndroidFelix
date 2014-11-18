package afelix.service.felixcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleException;

import android.content.Context;
import android.util.Log;

public class FelixControler {
	final private static String TAG = "FelixControler";
	
	private Felix felixFramework = null;
	private String res = new String();
	
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
		res = this.MainControler(bundle, location, command);
		return res;
	}
	
	public String start(String bundle, int command){
		Log.d(TAG, "About to start bundle: " + bundle);
		res = this.MainControler(bundle, "", command);
		return res;
	}
 
	
	private String MainControler(String bundle, String location, int command){
		
		//Main command execution block
		switch(command){
		case 1:
			return "Bundle:" + bundle + "has uninstalled successfully";
		case 2://install bundle
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
				return "File open failed.";
			}
				
			try{
				this.felixFramework.getBundleContext().installBundle(bundle, bs);
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
				
			return "Bundle:" + bundle + "has installed successfully";
		case 16:
			return "Bundle:" + bundle + "has stoped successfully";
		case 32://start bundle
			long bid = -1;
				
			org.osgi.framework.Bundle[] bl = felixFramework.getBundleContext().getBundles();
	        for (int i = 0; bl != null && i < bl.length; i++) {
	            if (bundle.equals(bl[i].getLocation())) {
	                bid = bl[i].getBundleId();
	            }
	        }

	        org.osgi.framework.Bundle b = felixFramework.getBundleContext().getBundle(bid);
	        if (b == null) {
	        	System.out.println("Can't find bundle " + bundle);
	            return "Can't find bundle " + bundle;
	        }

	        try {
	            b.start(org.osgi.framework.Bundle.START_ACTIVATION_POLICY);
	            
	            System.out.println("bundle: " + b.getSymbolicName() + "/" + b.getBundleId() + "/"
	                    + b + " started");
	        } catch (BundleException be) {
	        	System.out.println(be.toString());
	        }
			return "Bundle:" + bundle + "has started successfully";
		default:
			return "Invalid command.";   
		}
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
	
	
	public ArrayList<String> BundleInfo(Felix f, int command){
		ArrayList<String> as = new ArrayList<String>();
		
		for(org.osgi.framework.Bundle b : f.getBundleContext().getBundles()) {
			switch(command){
			case 1:
				as.add(Long.toString(b.getBundleId()));
				break;
			case 2:
				as.add(b.getSymbolicName());
				break;
			case 4:
				as.add(b.getBundleId()+" \t\t" + b.getSymbolicName() + "\t\t"+b.getState());
				break;
			default:
				Log.e(TAG, "Invalid command.");
			}
		}
		return as;
	}
}
