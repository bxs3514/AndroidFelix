package afelix.service.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Environment;
import android.util.Log;

public class SocketTransfer {
	private String ip;
	private int port;
	private String location;
	private String bundleName;
	
	private Socket mSocket;
	
	public SocketTransfer(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void sendBundle(String location, String bundleName){
		this.location = location;
		this.bundleName = bundleName;
		if(location == null){
			location = Environment.getExternalStorageDirectory().getPath() + "/AFelixData/Bundle/";
		}

		final File myFile = new File (location + bundleName);
        final byte [] mybytearray  = new byte [(int)myFile.length()];
        Log.e("Client", Integer.toString(mybytearray.length));
        
		new Thread(){
			public void run(){
				 try{
				 mSocket = new Socket(ip, port);  //connect to server
				 
                 //FileInputStream fis = new FileInputStream(myFile);
                 //BufferedInputStream bis = new BufferedInputStream(fis);
                 //bis.read(mybytearray,0,mybytearray.length);
                 //BufferedOutputStream out = new BufferedOutputStream(os);
                 OutputStream os = mSocket.getOutputStream();
                 DataOutputStream d = new DataOutputStream(os);
                 System.out.println("Sending...");
                 d.writeUTF(myFile.getName());
                 d.flush();
                 d.writeLong(myFile.length());
                 d.flush();
                 d.write(mybytearray,0,mybytearray.length);
                 d.flush();
				 d.close();
                 
				 mSocket.close();   //closing the connection
				 }catch (UnknownHostException e) {
					 e.printStackTrace();
					} catch (IOException e) {
					 e.printStackTrace();
					}
			 }
		}.start();
	}
}
