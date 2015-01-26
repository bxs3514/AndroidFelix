

/**
 * 
 * @author bxs3514
 *
 * The realize of BundlePresent for IPC
 *
 * @lastEdit 01/26/2015
 * 
 */

package afelix.service.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

public class BundlePresent implements Parcelable{
	
	private Map<String, List<Object>> BundleResult;

	public BundlePresent(){
		
	}
	
	private BundlePresent(Parcel in){
		readFromParcel(in);
	}
	
	private void readFromParcel(Parcel in) {
		BundleResult = in.readHashMap(Map.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeMap(BundleResult);
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

	public void setBundleResult(String type, Object...result){
		List<Object> resultList = BundleResult.get(type);
		if(resultList == null){
			resultList = new ArrayList<Object>();
		}
		for(Object res : result){
			resultList.add(res);
		}
		BundleResult.put(type, resultList);
	}

	public void setBundleResult(Map<String, List<Object>> bundleResult) {
		BundleResult = bundleResult;
	}
	
	public Map<String, List<Object>> getBundleResult() {
		return BundleResult;
	}

}
