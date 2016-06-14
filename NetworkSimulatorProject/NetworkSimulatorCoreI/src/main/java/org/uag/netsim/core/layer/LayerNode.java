package org.uag.netsim.core.layer;

import java.net.InetAddress;

public interface LayerNode extends Runnable{
	
	static int MSG_LENGHT = 512;

	boolean isReady();
	
	boolean isBusy();
	
	int getActiveCount();
	
	int getAvailablePort();
	
	void releasePort(int port);
	
	void release() throws Exception;
	
	LayerTcpConnection openTcpConnection() throws Exception;
	
	void closeTcpConnection(int tcpPort) throws Exception;
	
	LayerTcpConnection getAvailableTcpConnection();
	
	int getPort();
	
	InetAddress getHost();
	
	//int getMaxPort();
	
	//List<Integer> getOpenedPorts();
	
}
