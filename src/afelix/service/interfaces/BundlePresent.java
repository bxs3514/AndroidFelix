

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class BundlePresent implements Parcelable{
	
	private String path;
	private String bundlePack;
	private String className;
	private String methodName;
	private String resKey;
	private Object[] parameter;
	private List<String> clazz;
	private Map<String, List<Object>> BundleResult;
	
	public BundlePresent(){
		BundleResult = new HashMap<String, List<Object>>();
	}
	
	private BundlePresent(Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		path = in.readString();
		bundlePack = in.readString();
		className = in.readString();
		methodName = in.readString();
		resKey = in.readString();
		parameter = in.readArray(Object.class.getClassLoader());
		clazz = in.readArrayList(List.class.getClassLoader());
		BundleResult = in.readHashMap(Map.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(path);
		out.writeString(bundlePack);
		out.writeString(className);
		out.writeString(methodName);
		out.writeString(resKey);
		out.writeArray(parameter);
		out.writeList(clazz);
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

	public void setBundleResult(String resKey, Object...result){
		List<Object> resultList = null;
		try{
			resultList = BundleResult.get(resKey);
			for(Object res : result){
				resultList.add(res);
			}
		}catch(NullPointerException ne){
			resultList = new ArrayList<Object>();
			for(Object res : result){
				resultList.add(res);
			}
			BundleResult.put(resKey, resultList);
		}
	}

	public void setBundleResult(Map<String, List<Object>> bundleResult) {
		BundleResult = bundleResult;
	}
	
	public Map<String, List<Object>> getBundleResult() {
		return BundleResult;
	}
	
	public void setParameters(String path, String bundlePack, 
			String className, String methodName, String resKey, Object[] parameter, 
			ArrayList<String> clazz){
		this.path = path;
		this.bundlePack = bundlePack;
		this.className = className;
		this.methodName = methodName;
		this.resKey = resKey;
		this.parameter = parameter;
		this.clazz = clazz;
	}

	public String getPath() {
		return path;
	}

	public String getBundlePack() {
		return bundlePack;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public Object[] getParameter() {
		return parameter;
	}

	public List<String> getClazz() {
		return clazz;
	}

	public String getResKey() {
		return resKey;
	}

	public static Parcelable.Creator<BundlePresent> getCreator() {
		return CREATOR;
	}
}
