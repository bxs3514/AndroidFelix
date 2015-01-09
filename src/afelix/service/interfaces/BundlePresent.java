

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

import android.os.Parcel;
import android.os.Parcelable;

public class BundlePresent implements Parcelable{
	
	private String result;

	public BundlePresent(){
		
	}
	
	private BundlePresent(Parcel in){
		readFromParcel(in);
	}
	
	private void readFromParcel(Parcel in) {
		//resBundle = (Bundle) in.readValue(Bundle.class.getClassLoader());
		result = (String)in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		//out.writeValue(resBundle);
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

	public String getResBundle() {
		return result;
	}

	public void setResBundle(String bundle){
		
	}
}
