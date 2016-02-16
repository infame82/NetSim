package org.uag.netsim.core.layer;

import java.net.InetAddress;

public abstract class AbstractLayerTcpNodeHandler implements LayerTcpNodeHandler{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7225073138922718712L;
	private int port;
	private InetAddress host;
	private int noise;
	
	public AbstractLayerTcpNodeHandler(InetAddress host,int port,int noise){
		this.host = host;
		this.port = port;
		this.noise = noise;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public InetAddress getHost() {
		return host;
	}

	public void setHost(InetAddress host) {
		this.host = host;
	}

	public int getNoise() {
		return noise;
	}

	public void setNoise(int noise) {
		this.noise = noise;
	}

}
