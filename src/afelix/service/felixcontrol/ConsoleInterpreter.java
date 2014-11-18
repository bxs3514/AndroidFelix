
/**
 * 
 * @author bxs3514
 *
 * This is an android felix console interpreter.
 *
 * @lastEdit 11/18/2014
 * 
 */

package afelix.service.felixcontrol;

import java.util.ArrayList;
import java.util.Iterator;
import android.util.Log;


public class ConsoleInterpreter{
	private final static String TAG = "ConsoleInterpreter";
	
	private String command = null;
	private FelixControler fc = null;
	
	private boolean res;
	
	public ConsoleInterpreter(FelixControler fc) {
		this.fc = fc;
	}
	
	public boolean interpret(String command){
		this.command = command.toLowerCase();
		String[] cwords = command.split(" ");
		if(cwords[0].equals("ss")){
			String res = new String();
			ArrayList<String> as =  fc.BundleInfo(4);
			Iterator<String> it = as.iterator();
			
			while(it.hasNext()){
				res += it.next() + "\n";
			}
			//return res;
		}
		else if(cwords[0].equals("start") || cwords[0].equals("stop")
				|| cwords[0].equals("uninstall")){
			if(cwords.length != 2){
				Log.e(TAG, "Wrong command!");
				return false;
			}
			
			if(cwords[0].equals("start")) fc.start(cwords[1]);
			else if(cwords[0].equals("stop")) fc.stop(cwords[1]);
			else if	(cwords[0].equals("uninstall")) fc.uninstall(cwords[1]);
			else{
				Log.e(TAG, "Wrong command!");
				return false;
			}
		}
		else if(cwords[0].equals("install")){
			if(cwords.length != 3){
				Log.e(TAG, "Wrong command!");
				return false;
			}
			fc.install(cwords[2], cwords[1]);
		}
		else{
			Log.e(TAG, "Wrong command!");
			return false;
		}
		
		return true;
	}
}
