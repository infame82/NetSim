package org.uag.netsim.core.layer;

import java.io.Serializable;
import java.net.InetAddress;

public interface LayerNodeHandler extends Serializable {

	int getPort();
	
	InetAddress getHost();
	
	int getNoise();
}
