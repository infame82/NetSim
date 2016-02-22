package org.uag.netsim.core.layer;

public interface LayerClient {

	void discoverNodes() throws Exception;
	
	LayerTcpConnectionHandler requestTcpNode() throws Exception;
}
