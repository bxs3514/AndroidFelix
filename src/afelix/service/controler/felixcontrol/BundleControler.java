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

import java.util.ArrayList;

import android.content.Context;

public interface BundleControler {
	public String install(String bundle, String location);
	public String install(Context context, String bundle, int command);
	public String uninstall(String bundle);
	public String start(String bundle);
	public String stop(String bundle);
	public String find(String bundle);
	public ArrayList<String> BundleInfo(int command);
}
