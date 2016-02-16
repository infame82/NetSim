package org.uag.netsim.core.layer.app;

import java.net.InetAddress;

import org.uag.netsim.core.layer.AbstractLayerTcpNodeHandler;

public class AppLayerTcpNodeHandler extends AbstractLayerTcpNodeHandler{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 205932797716367585L;
	
	public AppLayerTcpNodeHandler(InetAddress host, int port, int noise) {
		super(host, port, noise);
	}


}
