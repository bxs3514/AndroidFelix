

/**
 * 
 * @author bxs3514
 *
 * The realize of BundlePresent for IPC
 *
 * @lastEdit 11/9/2014
 * 
 */

package afelix.service.interfaces;

import java.util.HashMap;

import org.osgi.framework.Bundle;

import android.os.Parcel;
import android.os.Parcelable;

public class BundlePresent implements Parcelable{
	
	private HashMap<String, Bundle> hs;

	public BundlePresent(){
		
	}
	
	private BundlePresent(Parcel in){
		readFromParcel(in);
	}
	
	private void readFromParcel(Parcel in) {
		hs = (HashMap<String, Bundle>) in.readValue(HashMap.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeMap(hs);
	} 
	
	public static final Parcelable.Creator<BundlePresent> CREATOR 
			= new Parcelable.Creator<BundlePresent>(){

			@Override
			public BundlePresent createFromParcel(Parcel in) {
					
				return new BundlePresent(in);
			}

			@Override
			public BundlePresent[] newArray(int size) {	
				return new BundlePresent[size];
			}
	};

	public HashMap<String, Bundle> getResBundleHs() {
		return hs;
	}

	public void setResBundle(Bundle resBundle) {
		hs.put("bundle", resBundle);
	}

}
