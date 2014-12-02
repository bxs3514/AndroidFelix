
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

import java.util.ArrayList;
import java.util.Iterator;

import afelix.service.controler.file.FileControler;
import android.os.Environment;
import android.util.Log;


public class ConsoleInterpreter{
	private final static String TAG = "ConsoleInterpreter";
	
	private FelixControler fc = null;
	private FileControler filecontrol = null;
	//private File[] f = null;
	
	private String command = null;
	private String defaultPath = null;
	private String res;
	
	public ConsoleInterpreter(FelixControler fc) {
		this.fc = fc;
		filecontrol = new FileControler();
		filecontrol.initAFelixFile();

		defaultPath = Environment.getExternalStorageDirectory().getPath();
		
		defaultPath = Environment.getExternalStorageDirectory().getPath() + "/AFelixData/Bundle/";
	}
	
	
	public String getDefaultPath() {
		return defaultPath;
	}


	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}


	public String interpret(String command){
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
				|| cwords[0].equals("uninstall")){
			if(cwords.length != 2){
				Log.e(TAG, "Wrong command!");
				return "Wrong command!";
			}
			
			if(cwords[0].equals("start")) return(fc.start(cwords[1]));
			else if(cwords[0].equals("stop")) return(fc.stop(cwords[1]));
			else if	(cwords[0].equals("uninstall")) return(fc.uninstall(cwords[1]));
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
		else{
			Log.e(TAG, "Wrong command!");
			return "Wrong command!";
		}
	}
}
