
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

interface IAFelixService{
	
	void startFelix();
	void installBundle(String bundle);
	void installBundleByLocation(String bundle, String location);
	void uninstallBundle(String bundle_id);
	void startBundle(String bundle);
	void stopBundle(String bundle);
	int getBundleId(String bundle);
	BundlePresent getBundlesContainer(String bundle);
	String dependency(String bundle);
	
}
