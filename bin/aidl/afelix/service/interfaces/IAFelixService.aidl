
/**
 * 
 * @author bxs3514
 *
 * This is the android felix manage service interface 
 * that expose to the user
 *
 */

package afelix.service.interfaces;

import afelix.service.interfaces.BundlePresent;
import java.util.List;
import java.lang.Object;

interface IAFelixService{
	void startFelix();
	void stopFelix();
	void installBundle(String bundle);
	void installBundleByLocation(String bundle, String location);
	void uninstallBundle(String bundle_id);
	void startBundle(String bundle);
	void stopBundle(String bundle);
	void resteartBundle(String bundle);
	void updateBundle(String bundle);
	void sendBundle(String bundle);
	void sendBundleOnPosition(String position, String bundle);
	List<String> getAll();
	int getBundleId(String bundle);
	BundlePresent executeBundle(inout BundlePresent bundle);
	BundlePresent executeExistBundle(inout BundlePresent bundle);
	BundlePresent getBundlesContainer(String bundle);
	String dependency(String bundle);
	boolean interpret(String command);
}
