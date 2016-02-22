package org.uag.netsim.core.layer;

import java.net.InetAddress;

public interface LayerTcpConnection extends Runnable{

	static int MSG_LENGHT = 512;
	
	int getPort();
	
	InetAddress getHost();

	boolean isBusy();
	
	int getActiveCount();

	void release();
}
