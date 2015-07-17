package afelix.service.net;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.net.TrafficStats;
import android.os.Environment;
import android.util.Log;

public class SocketTransfer {
	private String ip;
	private int port;
	private String location;
	private String bundleName;
	
	private long lastTxByte;
	private long lastRxByte;
	private float networkTxByte;
	private float networkRxByte;
	private long lastTimeStamp;
	private float networkTxSpeed;
	private float networkRxSpeed;
	
	private Socket mSocket;
	
	public TimerTask mTimerTask = new TimerTask(){
		
		public void run(){
			measureNetSpeed();
		}
	};
	
	public SocketTransfer(String ip, int port){
		this.ip = ip;
		this.port = port;
		lastTxByte = TrafficStats.getTotalTxBytes();
		lastRxByte = TrafficStats.getTotalRxBytes();
		lastTimeStamp = System.currentTimeMillis();
		networkTxSpeed = networkRxSpeed = 0;
		new Timer().schedule(mTimerTask, 1000, 1000);
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
	
	public float getNetworkTxSpeed() {
		return networkTxSpeed;
	}

	public float getNetworkRxSpeed() {
		return networkRxSpeed;
	}

	public void sendBundle(String location, final String bundleName){
		this.location = location;
		this.bundleName = bundleName;
		if(location == null){
			location = Environment.getExternalStorageDirectory().getPath() + "/AFelixData/Bundle/";
		}

		final File myFile = new File (location + bundleName);
        final byte [] mybytearray  = new byte [(int)myFile.length()];
        if(!bundleName.equals("Speed.test"))
        	Log.e("Client", Integer.toString(mybytearray.length));
        
		new Thread(){
			public void run(){
				 try{
				 System.out.println(ip + ":" + port);
				 mSocket = new Socket(ip, port);  //connect to server
				 
                 FileInputStream fis = new FileInputStream(myFile);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 bis.read(mybytearray,0,mybytearray.length);
                 //BufferedOutputStream out = new BufferedOutputStream(os);
                 //OutputStream os = mSocket.getOutputStream();
                 DataOutputStream d = new DataOutputStream(mSocket.getOutputStream());
                 
                 if(!bundleName.equals("Speed.test"))
                 	System.out.println("Sending...");
                 
                 d.writeUTF(myFile.getName());
                 d.flush();
                 //d.writeLong(myFile.length());
                 d.writeLong(System.currentTimeMillis());
                 d.flush();
                 //long startTime = System.currentTimeMillis();
                 //while((bufByte = bis.read(mybytearray)) != -1)
                 d.write(mybytearray,0,mybytearray.length);
                 d.flush();
				 d.close();
                 
				 //System.out.println(System.currentTimeMillis() - startTime);
				 mSocket.close();   //closing the connection
				 }catch (UnknownHostException e) {
					 e.printStackTrace();
					} catch (IOException e) {
					 e.printStackTrace();
					}
			 } 
		}.start();
	}
	

	private void measureNetSpeed(){
		
		if(lastTxByte == TrafficStats.UNSUPPORTED || lastRxByte == TrafficStats.UNSUPPORTED){
			networkTxByte = networkRxByte = -1;
			return;
		}
		long nowTxByte = TrafficStats.getTotalTxBytes();
		long nowRxByte = TrafficStats.getTotalRxBytes();
		long nowTimeStamp = System.currentTimeMillis();
		
		networkTxByte = (float)(nowTxByte - lastTxByte);
		networkRxByte = (float)(nowRxByte - lastRxByte);
		if(networkTxSpeed == 0)
			networkTxSpeed = networkTxByte / (nowTimeStamp - lastTimeStamp);
		else 
			networkTxSpeed = networkTxSpeed * 0.3f + networkTxByte / (nowTimeStamp - lastTimeStamp);
		
		if(networkRxSpeed == 0)
			networkRxSpeed = networkRxByte / (nowTimeStamp - lastTimeStamp);
		else 
			networkRxSpeed = networkRxSpeed * 0.3f + networkRxByte / (nowTimeStamp - lastTimeStamp) * 0.7f;
		
		lastTxByte = nowTxByte;
		lastRxByte = nowRxByte;
		lastTimeStamp = nowTimeStamp;

		/*if(networkTxSpeed < 1)
			System.out.println(networkTxSpeed * 1024 + " B/s; " );
		else if(networkTxSpeed >= 1 && networkTxByte < 1024)
			System.out.println(networkTxSpeed + " KB/s; " );	
		else System.out.println(networkTxSpeed / 1024 + " MB/s;  " );
		
		if(networkRxSpeed < 1)
			System.out.println(networkRxSpeed * 1024 + " B/s\n " );
		else if(networkRxSpeed >= 1 && networkRxByte < 1024)
			System.out.println(networkRxSpeed + " KB/s\n " );	
		else System.out.println(networkRxSpeed / 1024 + " MB/s\n " );*/
	};
}
