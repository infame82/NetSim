package org.uag.netsim.core.layer;

import java.io.Serializable;
import java.net.InetAddress;

public interface LayerTcpConnectionHandler extends Serializable{

	int getPort();
	
	InetAddress getHost();
	
	int getNoise();
}
