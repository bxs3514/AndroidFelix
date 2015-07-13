package afelix.service.controler.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileControler implements IFileControler{

	private static final String TAG = "FileControler";
	
	private static final String AFelixDir = "AFelixData";
	private static final String BundleDir = "Bundle";
	private static final String DatabaseDir = "Database";
	
	private String location = null;
	private File currentDir = null;
	private File tempFile = null;

	public FileControler(){
		
		if(CheckFileSystemState()){
			this.location = Environment.getExternalStorageDirectory().getPath();
			currentDir = new File(location);
		}else{
			Log.e(TAG, "Can't find the external storate.");
		}
	}

	
	public FileControler(String location){
		if(CheckFileSystemState()){
			this.location = location;
			currentDir = new File(location);
		}else{
			Log.e(TAG, "Can't find the external storate.");
		}
	}
	
	
	@Override
	public void initAFelixFile() {
		// TODO Auto-generated method stub
		tempFile = new File(location + File.separator + AFelixDir);
		if(CheckFileSystemState()){
			if(!tempFile.exists()){
				tempFile.mkdirs();
			}
			

			tempFile = new File(location + File.separator + AFelixDir 
					+ File.separator + BundleDir);
			if(!tempFile.exists()){
				tempFile.mkdir();
			}
			tempFile = new File(location + File.separator + AFelixDir 
					+ File.separator + DatabaseDir);
			if(!tempFile.exists()){
				tempFile.mkdir();
			}
			writeFileSdcardFile(location + File.separator + AFelixDir + File.separator,
					"Speed.test", new byte[1024*1024]);
		}else{
			Log.e(TAG, "Can't find the external storate.");
		}
	}

	@Override
	public void mkDictionary(String location) {
		// TODO Auto-generated method stub
		tempFile = new File(location);
		if(CheckFileSystemState()){
			if(!tempFile.exists())
				if(tempFile.mkdirs()) Log.d(TAG, "Make dictionary successfully.");
				else Log.e(TAG, "Can't make dirctionary on" + location);
		}else{
			Log.e(TAG, "Can't find the external storate.");
		}
	}

	@Override
	public File[] getFileList(String location, String filter) {
		// TODO Auto-generated method stub
		currentDir = new File(location);
		return currentDir.listFiles();
	}


	private boolean CheckFileSystemState(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		else return false;
	}



	@Override
	public void readFileAssetsFile(Context context, String fileName) {
		// TODO Auto-generated method stub
		try{
			InputStream in = context.getApplicationContext().getAssets().open(fileName);
		}catch(IOException ie){
			Log.e(TAG, "Can't get file from assets", ie);
		}
	}



	@Override
	public void writeFileSdcardFile(String location, String fileName, byte[] write_str) {
		File file = new File(location, fileName);
		System.out.println(location + fileName);
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file);
			try {
				  outputStream.write(write_str);
				  outputStream.close();
				} catch (Exception e) {
				  e.printStackTrace();
				}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}
	

	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
		currentDir = new File(location);
	}

}
