

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
	public int id;
	public String symbolic_name;
	public String status;
	
	public BundlePresent(){
		
	}

	private BundlePresent(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		
		out.writeInt(id);
		out.writeString(symbolic_name);
		out.writeString(status);
	}
	
	public void readFromParcel(Parcel in){
		// Parcel Container for a message (data and object references) 
		// that can be sent through an IBinder. 
		this.id = in.readInt();
		this.symbolic_name = in.readString();
		this.status = in.readString();
	}
	

	public String allInfo(){
		return "id:\t" + id + "name:\t" + symbolic_name + "status:\t" + status;
	}
	
	public static final Parcelable.Creator<BundlePresent> CREATOR 
			= new Parcelable.Creator<BundlePresent>(){

				@Override
				public BundlePresent createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					
					return new BundlePresent(source);
				}

				@Override
				public BundlePresent[] newArray(int size) {
					// TODO Auto-generated method stub
					return new BundlePresent[size];
				}
		
		
	}; 

}
