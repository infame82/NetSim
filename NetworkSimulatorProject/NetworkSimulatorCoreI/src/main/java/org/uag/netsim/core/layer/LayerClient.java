package org.uag.netsim.core.layer;

import java.util.List;

public interface LayerClient {
	
	
	List<LayerNodeHandler> getNodeHandlers();
	
	void discoverNodes() throws Exception;
	
	LayerTcpConnectionHandler requestTcpNode() throws Exception;
	
	void enableLog(boolean enabled);
	
	
}
