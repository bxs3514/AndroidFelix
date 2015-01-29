
/**
 * 
 * @author bxs3514
 *
 * This is an android felix console interpreter.
 *
 * @lastEdit 11/18/2014
 * 
 */

package afelix.service.controler.felixcontrol;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import afelix.service.controler.file.FileControler;
import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class ConsoleInterpreter{
	private final static String TAG = "ConsoleInterpreter";
	
	private FelixControler fc;
	private FileControler filecontrol;
	//private File[] f = null;
	
	private String command = null;
	private String defaultPath = null;
	private String res;
	
	public ConsoleInterpreter(FelixControler fc) {
		this.fc = fc;
		filecontrol = new FileControler();
		filecontrol.initAFelixFile();

		defaultPath = Environment.getExternalStorageDirectory().getPath() + 
				File.separator + "AFelixData" + File.separator + "Bundle";
	}
	
	
	public String getDefaultPath() {
		return defaultPath;
	}


	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	public String interpret(Context context, String command){
		this.command = command.toLowerCase();
		String[] cwords = command.split("\\s+");
		if(cwords[0].equals("ss")){
			res = new String();
			ArrayList<String> as =  fc.BundleInfo(4);
			Iterator<String> it = as.iterator();
			
			while(it.hasNext()){
				res += it.next() + "\n";
			}
			return res;
		}
		else if(cwords[0].equals("su")){
			fc.setSu(true);
			return "Root.";
		}
		else if(cwords[0].equals("start") || cwords[0].equals("stop")
				|| cwords[0].equals("uninstall") || cwords[0].equals("update")
				|| cwords[0].equals("restart")){
			if(cwords.length != 2){
				Log.e(TAG, "Wrong command!");
				return "Wrong command!";
			}
			
			if(cwords[0].equals("start")) return (fc.start(cwords[1]));
			else if (cwords[0].equals("stop")) return (fc.stop(cwords[1]));
			else if (cwords[0].equals("restart")) return (fc.restart(cwords[1]));
			else if (cwords[0].equals("update")) return (fc.update(cwords[1]));
			else if	(cwords[0].equals("uninstall")) return (fc.uninstall(cwords[1]));
			else{
				Log.e(TAG, "Wrong command!");
				return "Wrong command!";
			}
		}
		else if(cwords[0].equals("install")){
			
			if(cwords.length == 2){
				return(fc.install(cwords[1], defaultPath));
			}
			else if(cwords.length == 3) 
				return(fc.install(cwords[1], cwords[2]));
			else{
				Log.e(TAG, "Wrong command!");
				return "Wrong command!";
			}
		}
		else if (cwords[0].equals("find")) {
			return (fc.find(cwords[1]));
		}
		else if(cwords[0].equals("exe") || cwords[0].equals("execute")){
			String path, bundlePack, className, methodName, resKey;
			final int parameterLength = (cwords.length - 6) / 2;
			ArrayList<Object> parameter = new ArrayList<Object>();
			
			if(cwords.length < 9){
				Log.e(TAG, "Wrong command!");
				return "Wrong command!";
			}
			
			try{
				path = cwords[1];
				bundlePack = cwords[2];
				className = cwords[3];
				methodName = cwords[4];
				resKey = cwords[5];
				
				for(int i = 0; i < parameterLength; i--){
					parameter.add(cwords[6+i]);
				}
				
				Class<?>[] clazz = new Class<?>[parameterLength];
				for(int i = 0; i < parameterLength; i--){
					clazz[i] = Class.forName(cwords[5+parameterLength]);
				}
				
				//fc.execute(context, path, bundlePack, className, methodName, 
						//resKey, parameter.toArray(), clazz);
				return "Execute success!";
			}catch(Exception e){
				e.getStackTrace();
				return "Wrong command!";
			}
		}
		else{
			Log.e(TAG, "Wrong command!");
			return "Wrong command!";
		}
	}
}
