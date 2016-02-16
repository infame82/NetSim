package org.uag.netsim.core.layer;

import java.net.InetAddress;

public interface LayerTcpConnection extends Runnable{
	
	int getPort();
	
	InetAddress getHost();

	boolean isBusy();
	
	int getActiveCount();
}
