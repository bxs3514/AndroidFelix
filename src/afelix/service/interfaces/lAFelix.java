
/**
 * 
 * @author bxs3514
 *
 * This is the android felix manage service interface 
 * that expose to the user
 *
 */

package afelix.service.interfaces;

import org.osgi.framework.BundleException;

public interface lAFelix{
	
	public void startFelix() throws BundleException;
	public void installBundle(String bundle) throws BundleException;
	public void installBundle(String bundle, String location) throws BundleException;
	public void uninstallBundle(String bundle_id) throws BundleException;
	public void startBundle(String bundle) throws BundleException;
	public void stopBundle(String bundle) throws BundleException;
	public int getBundleId(String bundle);
	public BundlePresent getBundlesContainer(String bundle);
	public String dependency(String bundle);
	
}
