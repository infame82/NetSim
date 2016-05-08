package org.uag.netsim.core.layer;

import java.util.List;

public interface LayerClient {
	
	//public final List<LayerNodeHandler> HANDLERS = new ArrayList<LayerNodeHandler>();
	
	List<LayerNodeHandler> getNodeHandlers();
	
	void discoverNodes() throws Exception;
	
	LayerTcpConnectionHandler requestTcpNode() throws Exception;
	
	void enableLog(boolean enabled);
	
	
}
