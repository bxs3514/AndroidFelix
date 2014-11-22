package afelix.mornitor.activity;

import java.util.ArrayList;

public class BundleInfo {
	private int BundleId;
	private String BundleName = null;
	private String Location = null;
	
	public int getBundleId() {
		return BundleId;
	}
	
	public void setBundleId(int bundleId) {
		BundleId = bundleId;
	}
	
	public String getBundleName() {
		return BundleName;
	}
	
	public void setBundleName(String bundleName) {
		BundleName = bundleName;
	}
	
	public String getLocation() {
		return Location;
	}
	
	public void setLocation(String location) {
		Location = location;
	}
	
	public ArrayList<Object> getAll(){
		ArrayList<Object> allInfo = new ArrayList<Object>();
		allInfo.add(BundleId);
		allInfo.add(BundleName);
		allInfo.add(Location);
		
		return allInfo;
	}
}
