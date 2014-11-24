package afelix.service.controler.file;

import java.io.File;

import android.content.Context;

public interface IFileControler {
	public String location = null;
	
	public void initAFelixFile();
	public void readFileAssetsFile(Context context, String fileName);
	public void writeFileSdcardFile(String fileName, String write_str);
	public void mkDictionary(String location);
	public File[] getFileList(String location, String filter);
}
