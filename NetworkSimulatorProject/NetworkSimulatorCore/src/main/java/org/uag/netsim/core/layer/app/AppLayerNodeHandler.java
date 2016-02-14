package org.uag.netsim.core.layer.app;

import java.net.InetAddress;

import org.uag.netsim.core.layer.AbstractLayerNodeHandler;

public class AppLayerNodeHandler extends AbstractLayerNodeHandler{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3743822209143204852L;
	
	public AppLayerNodeHandler(InetAddress host, int port,int noise) {
		super(host, port,noise);
	}

}
