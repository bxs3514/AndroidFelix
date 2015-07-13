
/**
 * 
 * @author bxs3514
 *
 * This is the android felix network service interface 
 * that expose to the user
 *
 */
 
package afelix.service.interfaces;


interface IAFelixNet{
	void setIp(int ip);
	int getIp();
	void setPort(int port);
	int getPort();
}